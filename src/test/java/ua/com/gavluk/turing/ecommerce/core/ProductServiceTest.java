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

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @Test
    @Sql("/schema-product.sql")
    @Sql("/data-product.sql")
    void search_by_query_1() throws JsonProcessingException {

        PageableList<Product> found = service.searchByQuery("fancy", true, new PagingSettings(1, 100));
        assertEquals(1, found.getRows().size(), "Only one test product contains 'fancy'");
        assertEquals(4L, found.getRows().get(0).getId().longValue(), "this is 4th product");

        found = service.searchByQuery("fancy French", true, new PagingSettings(1, 100));
        assertEquals(1, found.getRows().size(), "Only one test product contains 'fancy' and 'French");
        assertEquals(4L, found.getRows().get(0).getId().longValue(), "this is 4th product");

        found = service.searchByQuery("fancy french", true, new PagingSettings(1, 100));
        assertEquals(1, found.getRows().size(), "Only one test product contains 'fancy' and 'french' ignore case");
        assertEquals(4L, found.getRows().get(0).getId().longValue(), "this is 4th product");

        found = service.searchByQuery("the all", true, new PagingSettings(1, 100));
        assertEquals(2, found.getRows().size(), "Two products contain 'the' and 'all' ignore case at the same time");
        assertArrayEquals(found.getRows().stream().map((x)-> x.getId()).collect(Collectors.toList()).toArray(new Long[0]), new Long[] {2L,4L});

        found = service.searchByQuery("French Merchants", false, new PagingSettings(1, 100));
        assertEquals(2, found.getRows().size(), "Two different products contain 'Merchants' (2th) and 'French' (4th)");
        assertArrayEquals(found.getRows().stream().map((x)-> x.getId()).collect(Collectors.toList()).toArray(new Long[0]), new Long[] {2L,4L});

        //ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();
        //System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(found));
    }

    @Test
    @Sql("/schema-product.sql")
    @Sql("/data-product.sql")
    @Sql("/attributes.sql")
    void load_attribute_values() throws Exception {
        Product x = service.findById(1L).orElseThrow(
                ()->new Exception("Product 1 not found")
        );

        List<AttributeValue> attributeValues = x.getAttributeValues();

        System.out.println(attributeValues);

        assertTrue(attributeValues.size() > 0);

    }
}