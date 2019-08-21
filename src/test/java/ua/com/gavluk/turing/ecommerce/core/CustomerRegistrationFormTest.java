package ua.com.gavluk.turing.ecommerce.core;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class CustomerRegistrationFormTest {

    @Test
    void json_serialization() throws IOException {
        CustomerRegistrationForm x = new CustomerRegistrationForm("Jhon Snow", "snow@example.com", "Qwerty123!");
        ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();

        String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(x);

        System.out.println(json);

        CustomerRegistrationForm y = mapper.readValue(json, CustomerRegistrationForm.class);
        assertEquals(x.getName(), y.getName());
        assertEquals(x.getEmail(), y.getEmail());
        assertEquals(x.getPassword(), y.getPassword());

    }

}