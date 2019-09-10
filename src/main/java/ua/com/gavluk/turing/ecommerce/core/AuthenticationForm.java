package ua.com.gavluk.turing.ecommerce.core;

import org.springframework.security.core.parameters.P;
import ua.com.gavluk.turing.ecommerce.exceptions.ValidationException;
import ua.com.gavluk.turing.ecommerce.utils.CommonUtils;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.beans.ConstructorProperties;

public class AuthenticationForm {

    @NotBlank(message = ValidationException.VALIDATION_CONSTRAINT_MESSAGE_PREFIX + ":USR_02:The field(s) are/is required")
    @Email(message = ValidationException.VALIDATION_CONSTRAINT_MESSAGE_PREFIX + ":USR_03:The email is invalid")
    private final String email;

    /*
     * ^                 # start-of-string
     * (?=.*[0-9])       # a digit must occur at least once
     * (?=.*[a-z])       # a lower case letter must occur at least once
     * (?=.*[A-Z])       # an upper case letter must occur at least once
     * (?=.*[@#$%^&+=])  # a special character must occur at least once
     * (?=\S+$)          # no whitespace allowed in the entire string
     * .{8,}             # anything, at least eight places though
     * $                 # end-of-string
     */
    //@Pattern(regexp="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$")
    @Pattern(regexp="^[0-9a-zA-Z@#$%^&+=!]{6,40}$")
    private final String password;

    @ConstructorProperties({"email", "password"})
    public AuthenticationForm(@Email String email, @Pattern(regexp = "^[0-9a-zA-Z@#$%^&+=]{6,40}$") String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "AuthenticationForm{" +
                "email='" + email + '\'' +
                ", password='" + CommonUtils.maskString(password, 1,1) + '\'' +
                '}';
    }
}
