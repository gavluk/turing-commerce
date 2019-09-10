package ua.com.gavluk.turing.ecommerce.core;

import ua.com.gavluk.turing.ecommerce.exceptions.ValidationException;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.beans.ConstructorProperties;

public class CustomerRegistrationForm extends AuthenticationForm {

    @NotBlank(message = ValidationException.VALIDATION_CONSTRAINT_MESSAGE_PREFIX + ":USR_02:The field(s) are/is required")
    @Size(max=50, message = ValidationException.VALIDATION_CONSTRAINT_MESSAGE_PREFIX + ":USR_07:this field is too long")
    private  String name;

    @ConstructorProperties({"name", "email", "password"})
    public CustomerRegistrationForm(
            String name,
            String email,
            String password
    ) {
        super(email, password);
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
