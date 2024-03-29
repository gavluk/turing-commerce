package ua.com.gavluk.turing.ecommerce.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ua.com.gavluk.turing.ecommerce.core.repo.OrderItemRepository;
import ua.com.gavluk.turing.ecommerce.core.repo.OrderRepository;
import ua.com.gavluk.turing.ecommerce.exceptions.AuthException;
import ua.com.gavluk.turing.ecommerce.exceptions.InternalErrorException;
import ua.com.gavluk.turing.ecommerce.exceptions.NotFoundException;
import ua.com.gavluk.turing.ecommerce.exceptions.ValidationException;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderService {


    public static final String MAIL_TEMPLATE_ORDER_IS_PAYED = "order_is_payed";
    public static final String MAIL_TEMPLATE_ORDER_IS_NOT_PAYED = "order_is_not_payed";
    public static final String MAIL_CONTENT_KEY_OF_ORDER = "order";
    public static final String MAIL_CONTENT_KEY_OF_PAYMENT_STATUS = "paymentStatus";
    public static final String MAIL_CONTENT_KEY_OF_SHIPPING = "shipping";

    private final OrderRepository repository;
    private final ShoppingCartService cartService;
    private final ProductService productService;
    private final OrderItemRepository orderItemRepository;
    private final PaymentProvider paymentProvider;
    private final ShippingService shippingService;
    private final MailingService mailingService;
    private final Logger logger;

    @Autowired
    public OrderService(OrderRepository repository,
                        ShoppingCartService cartService,
                        ProductService productService,
                        OrderItemRepository orderItemRepository,
                        PaymentProvider paymentProvider,
                        ShippingService shippingService, MailingService mailingService)
    {
        this.logger = LoggerFactory.getLogger(this.getClass());
        this.repository = repository;
        this.cartService = cartService;
        this.productService = productService;
        this.orderItemRepository = orderItemRepository;
        this.paymentProvider = paymentProvider;
        this.shippingService = shippingService;
        this.mailingService = mailingService;
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

        BigDecimal total = order.getTotalAmount();

        // obtaining product info and build items
        List<OrderItem> orderItems = new ArrayList<>();
        for (ShoppingCartItem cartItem:shoppingCartItems) {
            Product product = this.productService.findById(cartItem.getProductId()).orElseThrow(
                    ()-> new IllegalStateException("Cart item has product which cannot be found")
            );
            OrderItem item = new OrderItem(order, cartItem, product);
            item = this.orderItemRepository.save(item);
            orderItems.add(item);

            // calc total
            total = total.add(cartItem.getSubtotal());

        }
        order.setOrderItems(orderItems);

        // adding taxes
        total = total.add(total.multiply(tax.getTaxPercentage().divide(BigDecimal.valueOf(100))));
        // adding shipment
        total = total.add(shipping.getShippingCost());

        order.setTotalAmount(total);

        order = this.repository.save(order);

        // cleaning shopping cart
        // todo: Q: is it required to clean it right after order creation?
        this.cartService.emptyShoppingCart(cartId);

        return order;
    }

    // 10.1 POST PAYMENT TO STRIPE
    public PaymentStatus payOrder(Order order, PaymentCredentials credentials, Customer customer, String emailToSendReceipt)
            throws InternalErrorException, ValidationException {
        // pay order
        PaymentStatus paymentStatus = this.paymentProvider.pay(order, credentials, customer);

        if (paymentStatus.isSucceeded()) {

            // update order status
            order.setStatus(Order.STATUS_PAYED);

            // update order shipping date
            Long shippingId = order.getShippingId();
            Long orderId = order.getId();
            Shipping shipping = this.shippingService.findById(shippingId).orElseThrow(
                    ()->new IllegalStateException("Shipping #" + shippingId + " not found for order #" + orderId)
            );
            // todo: Q: do we need to update shipped_on date when order is payed? how to calculate that?
            order.setShippedOn(Instant.now());

            order = this.repository.save(order);
        }

        // notify happy or sad mail

        // load details to be possible use it in mail templates
        if (order.getOrderItems() == null) {
            List<OrderItem> items = this.orderItemRepository.findByOrderId(order.getId());
            order.setOrderItems(items);
        }

        Order finalOrder = order;
        Shipping shipping = this.shippingService.findById(order.getShippingId()).orElseThrow(
                ()-> new IllegalStateException("Cannot find shipping " + finalOrder.getShippingId() + " of order " + finalOrder.getId())
        );
        try {
            this.mailingService.send(
                    customer,
                    paymentStatus.isSucceeded() ? MAIL_TEMPLATE_ORDER_IS_PAYED : MAIL_TEMPLATE_ORDER_IS_NOT_PAYED,
                    new HashMap<String, Object>() {{
                        put(MAIL_CONTENT_KEY_OF_ORDER, finalOrder);
                        put(MAIL_CONTENT_KEY_OF_PAYMENT_STATUS, paymentStatus);
                        put(MAIL_CONTENT_KEY_OF_SHIPPING, shipping);
                    }});
        } catch (Exception e) {
            this.logger.error("Unable to send mail to " + customer + " about " + order + " have payment status " + paymentStatus, e);
        }

        return paymentStatus;
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
