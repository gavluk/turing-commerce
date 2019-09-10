package ua.com.gavluk.turing.ecommerce.api;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import ua.com.gavluk.turing.ecommerce.core.stripe.StripePaymentStatus;

import java.io.IOException;
import java.util.Map;

public class StripeChargeResponseDto {
    @JsonIgnore
    private StripePaymentStatus paymentStatus;
    private final String message;

    private Map<String, Object> chargeJson;

    StripeChargeResponseDto(Map<String, Object> chargeJson, String message) {
        this.chargeJson = chargeJson;
        this.message = message;
    }

    StripeChargeResponseDto(StripePaymentStatus paymentStatus, String message) throws IOException {
        this.paymentStatus = paymentStatus;
        this.message = message;
        this.chargeJson = new ObjectMapper().readValue(this.paymentStatus.getCharge().toJson(), new TypeReference<Map<String, Object>>() {});
    }

    @JsonAnyGetter
    public Map<String, Object> getChargeJson() {
        return this.chargeJson;
    }

    public String getMessage() {
        return this.message;
    }

}
