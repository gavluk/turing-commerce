package ua.com.gavluk.turing.ecommerce.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ua.com.gavluk.turing.ecommerce.core.Customer;
import ua.com.gavluk.turing.ecommerce.core.CustomerRegistrationForm;
import ua.com.gavluk.turing.ecommerce.core.CustomerService;
import ua.com.gavluk.turing.ecommerce.exceptions.ValidationException;

import javax.validation.Valid;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService service;

    @Autowired
    public CustomerController(CustomerService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Customer registerNewCustomer(@Valid @RequestBody CustomerRegistrationForm form) throws ValidationException {
        return this.service.registerNewCustomer(form);
    }
}
