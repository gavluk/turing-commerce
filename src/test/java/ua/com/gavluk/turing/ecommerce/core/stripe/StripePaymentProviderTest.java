package ua.com.gavluk.turing.ecommerce.core.stripe;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Token;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class StripePaymentProviderTest {

    private static final String API_KEY = "sk_test_lomdOfxbm7QDgZWvR82UhV6D"; // "sk_test_4eC39HqLyjWDarjtT1zdp7dc";

    @Test
    void create_some_token() throws StripeException {

        Stripe.apiKey = API_KEY;

        Map<String, Object> tokenParams = new HashMap<String, Object>();
        Map<String, Object> cardParams = new HashMap<String, Object>();
        cardParams.put("number", "4242424242424242");
        cardParams.put("exp_month", 9);
        cardParams.put("exp_year", 2020);
        cardParams.put("cvc", "314");
        tokenParams.put("card", cardParams);

        Token x = Token.create(tokenParams);
        assertNotNull(x);

        Token y = Token.retrieve(x.getId());
        assertEquals(x.getId(), y.getId());

        System.out.println(x);
    }

}