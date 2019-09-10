package ua.com.gavluk.turing.ecommerce.core.stripe;

import ua.com.gavluk.turing.ecommerce.core.PaymentCredentials;

public class StripePaymentCredentials implements PaymentCredentials {

    private final String token;

    public StripePaymentCredentials(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
