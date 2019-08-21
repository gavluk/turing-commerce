package ua.com.gavluk.turing.ecommerce.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ua.com.gavluk.turing.ecommerce.core.repo.CustomerRepository;
import ua.com.gavluk.turing.ecommerce.exceptions.AuthException;
import ua.com.gavluk.turing.ecommerce.exceptions.ValidationException;

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
}
