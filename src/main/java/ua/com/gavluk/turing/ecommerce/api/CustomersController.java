package ua.com.gavluk.turing.ecommerce.api;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.JWTClaimsSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ua.com.gavluk.turing.ecommerce.core.AuthenticationForm;
import ua.com.gavluk.turing.ecommerce.core.Customer;
import ua.com.gavluk.turing.ecommerce.core.CustomerRegistrationForm;
import ua.com.gavluk.turing.ecommerce.core.CustomerService;
import ua.com.gavluk.turing.ecommerce.exceptions.AuthException;
import ua.com.gavluk.turing.ecommerce.exceptions.ValidationException;

import javax.validation.Valid;

@RestController
@RequestMapping("/customers")
public class CustomersController {

    private final CustomerService service;
    private final JwtUtils jwtUtils;

    @Autowired
    public CustomersController(CustomerService service, JwtUtils jwtUtils) {
        this.service = service;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Customer registerNewCustomer(@Valid @RequestBody CustomerRegistrationForm form) throws ValidationException {
        return this.service.registerNewCustomer(form);
    }

    @PostMapping("/login")
    public CustomerAuthenticatedDTO login(@Valid @RequestBody AuthenticationForm form) throws AuthException, JOSEException {
        Customer customer = this.service.authenticate(form);
        String token = this.jwtUtils.issuJwtTokenFor(customer);

        // a bit over-work for parsing but it's useful to display expired and maybe some more in future
        JWTClaimsSet jwtClaims = this.jwtUtils.validateToken(token);

        return new CustomerAuthenticatedDTO(customer, token, jwtClaims.getExpirationTime().toInstant());
    }

    @GetMapping
    public Customer getCurrentCustomer(CustomerAuthentication authentication) {
        return authentication.getCustomer();
    }
}
