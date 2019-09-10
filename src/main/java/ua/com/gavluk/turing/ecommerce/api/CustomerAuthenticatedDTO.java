package ua.com.gavluk.turing.ecommerce.api;

import com.fasterxml.jackson.annotation.JsonFormat;
import ua.com.gavluk.turing.ecommerce.core.Customer;

import java.time.Instant;

class CustomerAuthenticatedDTO {

    private Customer customer;

    private String accessToken;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Instant expiresIn;

    public CustomerAuthenticatedDTO(Customer customer, String accessToken, Instant expiresIn) {
        this.customer = customer;
        this.accessToken = accessToken;
        this.expiresIn = expiresIn;
    }

    public Customer getCustomer() {
        return customer;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public Instant getExpiresIn() {
        return expiresIn;
    }
}
