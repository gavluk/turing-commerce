package ua.com.gavluk.turing.ecommerce.core;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.beans.ConstructorProperties;

public class CustomerRegistrationForm extends AuthenticationForm {

    @NotBlank
    @Size(max=50)
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
