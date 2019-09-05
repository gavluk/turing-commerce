package ua.com.gavluk.turing.ecommerce.api;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class StripeChargeRequestDto {

    @Positive
    @NotNull
    @JsonProperty("order_id")
    private Long orderId;

    @NotBlank
    @JsonProperty("stripeToken")
    private String stripeToken;

    @Email
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
