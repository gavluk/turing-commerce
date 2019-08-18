package ua.com.gavluk.turing.ecommerce.exceptions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthExceptionTest {
    @Test
    void test_error_to_json() throws JsonProcessingException {
        AuthException x = new AuthException(AuthException.ACCESS_UNAUTHORIZED);
        ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();

        String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(x);
        System.out.println(json); // visual control
        assertTrue(json.contains("code"), "Contains code");
        assertTrue(json.contains("status"), "Contains status");
        assertTrue(json.contains("message"), "Contains message");
        assertFalse(json.contains("field"), "Contains no field");
        assertFalse(json.contains("stack"), "Contain no stack trace");
    }
}