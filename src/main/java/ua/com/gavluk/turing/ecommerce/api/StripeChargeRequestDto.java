package ua.com.gavluk.turing.ecommerce.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import ua.com.gavluk.turing.ecommerce.exceptions.ValidationException;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class StripeChargeRequestDto {

    @Positive
    @NotNull(message = ValidationException.VALIDATION_CONSTRAINT_MESSAGE_PREFIX + ":USR_02:The field(s) are/is required")
    @JsonProperty("order_id")
    private Long orderId;

    @NotBlank(message = ValidationException.VALIDATION_CONSTRAINT_MESSAGE_PREFIX + ":USR_02:The field(s) are/is required")
    @JsonProperty("stripeToken")
    private String stripeToken;

    @Email(message = ValidationException.VALIDATION_CONSTRAINT_MESSAGE_PREFIX + ":USR_03:The email is invalid")
    @NotBlank(message = ValidationException.VALIDATION_CONSTRAINT_MESSAGE_PREFIX + ":USR_02:The field(s) are/is required")
    @JsonProperty("email")
    private String email;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getStripeToken() {
        return stripeToken;
    }

    public void setStripeToken(String stripeToken) {
        this.stripeToken = stripeToken;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
