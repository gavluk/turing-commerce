package ua.com.gavluk.turing.ecommerce.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class StripeChargeResponseDtoTest {

    @Test
    void json_unwrapped_serialization() throws JsonProcessingException {
        HashMap<String, Object> charge = new HashMap<>();
        charge.put("test1", "value1");
        charge.put("test2", "value2");
        StripeChargeResponseDto x = new StripeChargeResponseDto(charge, "Message");

        String json = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(x);

        System.out.println(json);


    }
}