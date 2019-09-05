package ua.com.gavluk.turing.ecommerce.core;

import ua.com.gavluk.turing.ecommerce.exceptions.InternalErrorException;
import ua.com.gavluk.turing.ecommerce.exceptions.ValidationException;

public interface PaymentProvider {

    PaymentStatus pay(Order order, PaymentCredentials credentials, Customer customer) throws InternalErrorException, ValidationException;

}
