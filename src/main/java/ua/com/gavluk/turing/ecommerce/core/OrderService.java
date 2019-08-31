package ua.com.gavluk.turing.ecommerce.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ua.com.gavluk.turing.ecommerce.core.repo.OrderItemRepository;
import ua.com.gavluk.turing.ecommerce.core.repo.OrderRepository;
import ua.com.gavluk.turing.ecommerce.exceptions.AuthException;
import ua.com.gavluk.turing.ecommerce.exceptions.NotFoundException;
import ua.com.gavluk.turing.ecommerce.exceptions.ValidationException;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderService {


    private final OrderRepository repository;
    private final ShoppingCartService cartService;
    private final ProductService productService;
    private final OrderItemRepository orderItemRepository;

    @Autowired
    public OrderService(OrderRepository repository, ShoppingCartService cartService, ProductService productService, OrderItemRepository orderItemRepository) {
        this.repository = repository;
        this.cartService = cartService;
        this.productService = productService;
        this.orderItemRepository = orderItemRepository;
    }

    /**
     *
     * @param customer customer who will be owner of this order
     * @param cartId cart from which the order must be created
     * @param shipping shipping to apply
     * @param tax tax to apply
     * @return created order
     * @throws ValidationException if shopping cart is empty
     */
    @Transactional
    public Order create(Customer customer, UUID cartId, Shipping shipping, Tax tax) throws ValidationException {
        Order order = new Order();

        // check if cart is not empty
        List<ShoppingCartItem> shoppingCartItems = this.cartService.fetchItemsOf(cartId);
        if (shoppingCartItems.isEmpty())
            throw new ValidationException(ValidationException.SHOPPING_CART_IS_EMPTY);

        // ### initialize & store new order
        order.setCustomer(customer);

        // set the rest of fields
        order.setCreatedOn(Instant.now());
        order.setShippingId(shipping.getId());
        order.setStatus(Order.STATUS_CREATED);
        order.setTaxId(tax.getId());

        // preliminary save to have order ID for order items
        order = this.repository.save(order);

        // obtaining product info and build items
        List<OrderItem> orderItems = new ArrayList<>();
        for (ShoppingCartItem cartItem:shoppingCartItems) {
            Product product = this.productService.findById(cartItem.getProductId()).orElseThrow(
                    ()-> new IllegalStateException("Cart item has product which cannot be found")
            );
            OrderItem item = new OrderItem(order, cartItem, product);
            item = this.orderItemRepository.save(item);
            orderItems.add(item);
        }
        order.setOrderItems(orderItems);

        order = this.repository.save(order);

        return order;
    }

    /**
     *
     * @param customer customer who asked order
     * @param orderId order id: must belong to customer
     * @return order with order details (order items)
     * @throws AuthException if order does not belong to customer
     * @throws NotFoundException if order with given <code>orderId</code> not found
     */
    public Order findById(Customer customer, Long orderId) throws AuthException, NotFoundException {

        Order order = this.repository.findById(orderId).orElseThrow(
                ()-> new NotFoundException(NotFoundException.ORDER_NOT_FOUND)
        );

        if (!customer.getId().equals(order.getCustomerId())) {
            throw new AuthException(AuthException.ACCESS_UNAUTHORIZED);
        }

        // order must have customer
        order.setCustomer(customer);

        return order;
    }

    public List<Order> findAllOrders(Customer customer) {

        List<Order> all = this.repository.findByCustomerId(customer.getId(), Sort.by(Sort.Direction.DESC, "createdOn"));

        // add customer as owner of each order
        return all.stream().peek((x)-> x.setCustomer(customer)).collect(Collectors.toList());
    }

    public Order findByIdWithDetails(Customer customer, Long orderId) throws AuthException, NotFoundException {

        // order already having customer
        Order order = this.findById(customer, orderId);

        // fill order items (details)
        List<OrderItem> items = this.orderItemRepository.findByOrderId(order.getId());
        order.setOrderItems(items);

        return order;
    }
}
