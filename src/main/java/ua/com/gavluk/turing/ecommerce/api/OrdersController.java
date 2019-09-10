package ua.com.gavluk.turing.ecommerce.api;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ua.com.gavluk.turing.ecommerce.core.*;
import ua.com.gavluk.turing.ecommerce.exceptions.AuthException;
import ua.com.gavluk.turing.ecommerce.exceptions.NotFoundException;
import ua.com.gavluk.turing.ecommerce.exceptions.ValidationException;

import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrdersController {

    private final OrderService service;
    private final ShoppingCartService cartService;
    private final ShippingService shippingService;
    private final TaxService taxService;

    @Autowired
    public OrdersController(OrderService service, ShoppingCartService cartService, ShippingService shippingService, TaxService taxService) {
        this.service = service;
        this.cartService = cartService;
        this.shippingService = shippingService;
        this.taxService = taxService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderCreationResponseDTO createNewOrder(@RequestBody CreateOrderRequestDTO req, CustomerAuthentication auth) throws ValidationException {

        Shipping shipping = this.shippingService.findById(req.getShippingId()).orElseThrow(
                ()-> new ValidationException(ValidationException.BAD_PARAMETER, "shipping_id")
        );

        Tax tax = this.taxService.findById(req.getTaxId()).orElseThrow(
                ()-> new ValidationException(ValidationException.BAD_PARAMETER, "tax_id")
        );

        Order order = this.service.create(auth.getCustomer(), req.getCartId(), shipping, tax);
        return new OrderCreationResponseDTO(order.getId());
    }

    @GetMapping("/{id}")
    @JsonView(ViewProfile.Minimal.class)
    public Order findCustomerOrderById(@PathVariable @Positive Long id, CustomerAuthentication auth) throws AuthException, NotFoundException {

        return this.service.findByIdWithDetails(auth.getCustomer(), id);
    }

    @GetMapping("/shortDetail/{id}")
    @JsonView(ViewProfile.Full.class)
    public Order findShortDetailedOrder(@PathVariable @Positive Long id, CustomerAuthentication auth) throws AuthException, NotFoundException {
        return this.service.findById(auth.getCustomer(), id);
    }

    @GetMapping("/inCustomer")
    @JsonView(ViewProfile.Basic.class)
    public List<Order> findCustomerOrders(CustomerAuthentication auth) {
        return this.service.findAllOrders(auth.getCustomer());
    }

}
