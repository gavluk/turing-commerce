package ua.com.gavluk.turing.ecommerce.core;

import com.fasterxml.jackson.annotation.JsonProperty;
import ua.com.gavluk.turing.ecommerce.exceptions.ValidationException;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class CreditCardForm {

    @JsonProperty("credit_card")
    @NotBlank(message = ValidationException.VALIDATION_CONSTRAINT_MESSAGE_PREFIX + ":USR_02:The field(s) are/is required")
    @Pattern(regexp = "^[\\dxX\\*]{16}$", message = ValidationException.VALIDATION_CONSTRAINT_MESSAGE_PREFIX + ":USR_08:Card must have 16 symbols: 'x', '*' for masking and digits, e.g. ************1234 or 4444xxxxxxxx1234", payload = {ValidationException.ProfileBuilder.class})
    private String creditCard;

    public String getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(String creditCard) {
        this.creditCard = creditCard;
    }
}
