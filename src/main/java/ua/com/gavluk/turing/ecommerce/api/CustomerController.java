package ua.com.gavluk.turing.ecommerce.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.com.gavluk.turing.ecommerce.core.Customer;
import ua.com.gavluk.turing.ecommerce.core.CustomerAddressForm;
import ua.com.gavluk.turing.ecommerce.core.CustomerDetailsForm;
import ua.com.gavluk.turing.ecommerce.core.CustomerService;
import ua.com.gavluk.turing.ecommerce.exceptions.ValidationException;

import javax.validation.Valid;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerService service;

    @Autowired
    public CustomerController(CustomerService service) {
        this.service = service;
    }


    @PutMapping
    public Customer updateCustomerDetails(@RequestBody @Valid CustomerDetailsForm form, CustomerAuthentication authentication) throws ValidationException {
        return this.service.updateDetails(authentication.getCustomer(), form);
    }

    @PutMapping("/address")
    public Customer updateCustomerAddress(@RequestBody @Valid CustomerAddressForm form, CustomerAuthentication authentication) throws ValidationException {
        return this.service.updateAddress(authentication.getCustomer(), form);
    }

}
