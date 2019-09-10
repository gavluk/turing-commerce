package ua.com.gavluk.turing.ecommerce.core;

import com.fasterxml.jackson.annotation.JsonProperty;
import ua.com.gavluk.turing.ecommerce.exceptions.ValidationException;

import javax.validation.constraints.NotBlank;

public class FacebookLoginForm {

    @NotBlank(message = ValidationException.VALIDATION_CONSTRAINT_MESSAGE_PREFIX + ":USR_02:The field(s) are/is required")
    @JsonProperty("access_token")
    private String accessToken;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
