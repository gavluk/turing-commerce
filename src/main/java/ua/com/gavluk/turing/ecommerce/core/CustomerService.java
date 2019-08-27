package ua.com.gavluk.turing.ecommerce.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ua.com.gavluk.turing.ecommerce.core.repo.CustomerRepository;
import ua.com.gavluk.turing.ecommerce.exceptions.AuthException;
import ua.com.gavluk.turing.ecommerce.exceptions.NotFoundException;
import ua.com.gavluk.turing.ecommerce.exceptions.ValidationException;

import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository repository;
    private PasswordEncoder passEncoder;

    private final Logger logger;

    @Autowired
    public CustomerService(CustomerRepository repository, PasswordEncoder passEncoder) {
        this.logger = LoggerFactory.getLogger(this.getClass());
        this.repository = repository;
        this.passEncoder = passEncoder;
    }

    public Customer authenticate(AuthenticationForm authForm) throws AuthException {

        Customer x = this.repository.findByEmail(authForm.getEmail()).orElseThrow(
                ()-> {
                    logger.warn("Authentication {} failed: user with such email not found", authForm);
                    return new AuthException(AuthException.ACCESS_UNAUTHORIZED);
                }
        );

        if (!passEncoder.matches(authForm.getPassword(), x.getPassword())) {
            logger.warn("Authentication {} failed: user password mismatch", authForm);
            // todo: store this incident and setup some user locking
            throw new AuthException(AuthException.ACCESS_UNAUTHORIZED);
        }

        // customer-user found and has valid password
        return x;
    }


    public Customer registerNewCustomer(CustomerRegistrationForm form) throws ValidationException {

        if (this.repository.findByEmail(form.getEmail()).isPresent())
            throw new ValidationException(ValidationException.EMAIL_EXISTS);

        String encodedPass = this.passEncoder.encode(form.getPassword());
        Customer customer = new Customer(form.getName(), form.getEmail(), encodedPass);

        return this.repository.save(customer);
    }

    public Optional<Customer> findById(Long id) {
        return this.repository.findById(id);
    }

    public Customer updateDetails(Customer customer, CustomerDetailsForm detailsForm) throws ValidationException {

        // check if email is not engaged
        Customer competitor = this.repository.findByEmail(detailsForm.getEmail()).orElse(customer);
        if (customer.equals(competitor)) {
            customer.setEmail(detailsForm.getEmail());
            customer.setName(detailsForm.getName());
            customer.setDayPhone(detailsForm.getDayPhone());
            customer.setMobPhone(detailsForm.getMobPhone());
            customer.setEvePhone(detailsForm.getEvePhone());
        }
        else
            throw new ValidationException(ValidationException.EMAIL_EXISTS);

        return this.repository.save(customer);
    }

    public Customer updateAddress(Customer customer, CustomerAddressForm addressForm) {
        // TODO: check if shipping region exists and valid

        customer.setAddress1(addressForm.getAddress1());
        customer.setAddress2(addressForm.getAddress2());
        customer.setCity(addressForm.getCity());
        customer.setRegion(addressForm.getRegion());
        customer.setCountry(addressForm.getCountry());
        customer.setPostalCode(addressForm.getPostalCode());
        customer.setShippingRegionId(addressForm.getShippingRegionId());

        return this.repository.save(customer);
    }

    public Customer authenticateOrRegister(FacebookLoginForm facebookCreds) throws AuthException, ValidationException {

        // e.g. https://graph.facebook.com/me?fields=email,name&access_token=<access-token>
        RestTemplate rest = new RestTemplate();
        FacebookProfileInfo fbProfile = rest.getForObject("https://graph.facebook.com/me?fields=email,name&access_token=" + facebookCreds.getAccessToken(), FacebookProfileInfo.class);

        // check if email granted (if not, error)
        if (fbProfile == null)
            throw new ValidationException(ValidationException.BAD_FACEBOOK_TOKEN);
        else if (fbProfile.getEmail() == null)
            throw new ValidationException(ValidationException.BAD_FACEBOOK_TOKEN, "email");

        // check if email is registered, if so return this customer
        Optional<Customer> candidate = this.repository.findByEmail(fbProfile.getEmail());

        // if not, register customer without password and return him
        return  candidate.orElseGet(()-> {
                Customer newCustomer = new Customer();
                newCustomer.setEmail(fbProfile.getEmail());
                newCustomer.setName(fbProfile.getName());
                this.repository.save(newCustomer);
                return newCustomer;
        });
    }

    public Customer updateCreditCard(Customer customer, CreditCardForm creditCardForm) {
        customer.setCreditCard(creditCardForm.getCreditCard());
        return this.repository.save(customer);
    }
}
