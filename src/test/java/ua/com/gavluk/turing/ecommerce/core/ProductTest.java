package ua.com.gavluk.turing.ecommerce.core;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    @Test
    void test_to_json() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();

        Product x = new Product(
                5L,
                "Marianne",
                "She symbolizes the \"Triumph of the Republic\" and has been depicted many different ways in the history of France, as you will see below!",
                BigDecimal.valueOf(15.95),
                BigDecimal.valueOf(14.90), // check for keeping tail zeros!!!
        "marianne.gif",
        "marianne-2.gif",
        "marianne-thumbnail.gif",
        2
        );

        String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(x);

        // just for quick visual-check
        System.out.println(json);

        assertTrue(json.contains("\"14.90\""), "Price converted to string and rounded keeping tail zeros");

    }
}