package ua.com.gavluk.turing.ecommerce.core;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ua.com.gavluk.turing.ecommerce.exceptions.AuthException;
import ua.com.gavluk.turing.ecommerce.exceptions.NotFoundException;
import ua.com.gavluk.turing.ecommerce.exceptions.ValidationException;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class OrderServiceTest {

    @Autowired
    private OrderService service;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private ShoppingCartService cartService;
    @Autowired
    private TaxService taxService;
    @Autowired
    private ShippingService shippingService;
    @Autowired
    private ProductService productService;

    @Test
    @Sql({
            "/schema-product.sql", "/schema-tax.sql", "/schema-shipping.sql", "/schema-order-and-details.sql",
            "/data-product.sql", "/data-shipping.sql", "/data-tax.sql"
    })
    void how_it_works_scenario() throws ValidationException, AuthException, NotFoundException {

        assertNotNull(service);
        assertNotNull(cartService);
        assertNotNull(shippingService);
        assertNotNull(taxService);


        // instantiate all necessary: customer, cart, shipping, tax, product
        Customer customer = customerService.registerNewCustomer(new CustomerRegistrationForm("test", "test@mail.com", "Qwerty123!"));
        UUID cartId = cartService.generateShoppingCartUniqueId();
        Shipping shipping = shippingService.findById(1L).orElseThrow(()->new IllegalStateException("test db has no shipping #1"));
        Tax tax = taxService.findById(1L).orElseThrow(()->new IllegalStateException("test db has no tax #1"));
        Product product = productService.findById(1L).orElseThrow(()->new IllegalStateException("test db has no sproduct #1"));

        Order x;

        // creating order from empty cart must fail...
        assertThrows(ValidationException.class, ()-> {
            service.create(customer, cartId, shipping, tax);
        }, "Empty cart exception here");

        // adding at least 1 item into cart
        cartService.addProductToShoppingCart(cartId, product, "", 2);

        // and now it should work
        x = service.create(customer, cartId, shipping, tax);


        // try to see what we created

        List<Order> allOrders = service.findAllOrders(customer);
        assertEquals(1, allOrders.size(), "customer must have one order");

        Order y = service.findById(customer, x.getId());
        assertNull(y.getOrderItems(), "no order details when asking just 'byId'");

        Order yPlus = service.findByIdWithDetails(customer, x.getId());
        assertNotNull(yPlus.getOrderItems(), "order details are there if asking 'withDetails");

    }
}