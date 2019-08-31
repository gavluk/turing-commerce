package ua.com.gavluk.turing.ecommerce.core;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class ShoppingCartServiceTest {

    @Autowired
    private ShoppingCartService service;

    @Autowired
    private ProductService productService;

    @Test
    @Sql("/schema-product.sql")
    @Sql("/data-product.sql")
    @Sql("/schema-shopping_cart.sql")
    void test_adding_same_product() {
        UUID cartId = service.generateShoppingCartUniqueId();
        Product product = productService.findById(1L).orElseThrow(()->new IllegalStateException("No products in test-set"));

        ShoppingCartItem x = service.addProductToShoppingCart(cartId, product, "", 2);
        assertEquals(2, x.getQuantity().intValue(), "Product added");

        x = service.addProductToShoppingCart(cartId, product, "", 3);
        assertEquals(5, x.getQuantity().intValue(), "Same product line merged");

        x = service.addProductToShoppingCart(cartId, product, "1", 3);
        assertEquals(3, x.getQuantity().intValue(), "Other attribute locks merging");

        x = service.addProductToShoppingCart(cartId, product, "1", 10);
        assertEquals(13, x.getQuantity().intValue(), "Merging  product");


    }



}