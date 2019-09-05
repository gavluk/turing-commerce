package ua.com.gavluk.turing.ecommerce.core.stripe;

import com.stripe.Stripe;
import com.stripe.exception.InvalidRequestException;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ua.com.gavluk.turing.ecommerce.core.*;
import ua.com.gavluk.turing.ecommerce.exceptions.InternalErrorException;
import ua.com.gavluk.turing.ecommerce.exceptions.ValidationException;

import java.util.Currency;
import java.util.HashMap;
import java.util.Map;

import static java.util.Optional.ofNullable;

@Service
public class StripePaymentProvider implements PaymentProvider {

    private final Logger logger;
    private Currency currency;

    public StripePaymentProvider(
            @Value("${stripe.api.key}")  String stripeApiKey,
            @Value("${stripe.connection.timeout:30000}") Integer stripeTimeoutMilliseconds,
            @Value("${shop.currency:USD}") String currencyStr
    )
    {
        this.logger = LoggerFactory.getLogger(this.getClass());
        this.currency = Currency.getInstance(currencyStr);
        Stripe.apiKey = ofNullable(stripeApiKey).orElseThrow(()-> new IllegalStateException("stripe.api.key is not configured"));
        Stripe.setConnectTimeout(stripeTimeoutMilliseconds);
    }

    @Override
    public PaymentStatus pay(Order order, PaymentCredentials credentials, Customer customer) throws InternalErrorException, ValidationException {
        Map<String, Object> chargeMap = new HashMap<>();
        Map<String, Object> metadata = new HashMap<>();

        // see example: https://stripe.com/docs/charges
        chargeMap.put("amount", order.getTotalAmount().movePointRight(this.currency.getDefaultFractionDigits()).intValueExact());
        chargeMap.put("currency", this.currency);
        chargeMap.put("description", "Turing order #" + order.getId());
        metadata.put("order_id", order.getId());
        chargeMap.put("metadata", metadata);
        chargeMap.put("source", ((StripePaymentCredentials)credentials).getToken() );

        try {
            Charge charge = Charge.create(chargeMap);
            return new StripePaymentStatus(charge);
        } catch (InvalidRequestException e) {
            logger.error("Error on stripe charge of order " + order, e);
            throw new ValidationException(ValidationException.BAD_PARAMETER, ofNullable(e.getParam()).orElse(e.getMessage()));
        } catch (StripeException e) {
            logger.error("Error on stripe charge of order " + order, e);
            throw new InternalErrorException(InternalErrorException.UNKNOWN_ERROR);
        }
    }
}
