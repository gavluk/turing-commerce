package ua.com.gavluk.turing.ecommerce.core;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ua.com.gavluk.turing.utils.PageableList;
import ua.com.gavluk.turing.utils.PagingSettings;
import ua.com.gavluk.turing.utils.SortOrder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class ProductServiceTest {

    @Autowired
    private ProductService service;

    @Test
    void service_instantiation() {
        assertNotNull(service);
    }

    @Test
    @Sql("/schema-product.sql")
    @Sql("/data-product.sql")
    void find_all_paging() throws JsonProcessingException {
        PageableList<Product> products = service.findAll(new PagingSettings(2, 2, "price", SortOrder.ASC));
        // for visual control
        System.out.println(new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(products));
        assertEquals(2, products.getRows().size(), "Found 3rd and 4th of 5");

        products = service.findAll(new PagingSettings(3, 2, "price", SortOrder.ASC));
        // for visual control
        System.out.println(new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(products));
        assertEquals(1, products.getRows().size(), "Found 5th of 5");

    }

    @Test
    @Sql("/schema-product.sql")
    @Sql("/data-product.sql")
    void test_by_id() throws Exception {
        Product x = service.findById(1L).orElseThrow(()->new Exception("Product 1 not found"));

        assertEquals(1L, x.getId().longValue());
    }
}