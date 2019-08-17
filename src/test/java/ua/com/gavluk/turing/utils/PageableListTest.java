package ua.com.gavluk.turing.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class PageableListTest {

    @Test
    void test_total_page_is_ok() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();
        PageableList<String> x = new PageableList<>(
                Arrays.asList("Item1", "Item2"),
                new PagingSettings(1L, 2, "name", SortOrder.DSC),
                5
        );

        assertEquals(3, x.getPaginationMeta().getTotalPages());
        assertEquals(5, x.getPaginationMeta().getTotalRecords());

        System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(x));

    }
}