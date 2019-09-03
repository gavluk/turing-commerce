package ua.com.gavluk.turing.ecommerce.core;

import com.fasterxml.jackson.annotation.*;
import ua.com.gavluk.turing.ecommerce.api.ViewProfile;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Entity
@Table(name="orders")
@JsonPropertyOrder({"order_id", "total_amount", "order_items", "created_on", "shipped_on", "status", "name"})
@JsonInclude(JsonInclude.Include.NON_ABSENT)
public class Order extends DbEntity {

    public static final Integer STATUS_CREATED = 0;
    public static final Integer STATUS_PAYED = 1;


    @Column(name="order_id", unique=true, nullable=false)
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @JsonProperty("order_id")
    @JsonView(ViewProfile.Minimal.class)
    private Long id;

    @Transient
    @JsonProperty("order_items")
    @JsonView(ViewProfile.Minimal.class)
    private List<OrderItem> orderItems;

    @Transient
    @JsonIgnore
    private Customer customer;

    @Column(name="customer_id", nullable=false)
    @JsonIgnore
    private Long customerId;

    @Column(name="total_amount", nullable=false)
    @JsonView(ViewProfile.Basic.class)
    @JsonProperty("total_amount")
    private BigDecimal totalAmount = new BigDecimal(0.00);

    @Column(name="created_on", nullable=false)
    @JsonProperty("created_on")
    @JsonView(ViewProfile.Basic.class)
    private Instant createdOn;

    @Column(name="shipped_on")
    @JsonProperty("shipped_on")
    @JsonView(ViewProfile.Basic.class)
    private Instant shippedOn;

    // todo: Q: what is order status? which values could be there and when?
    @Column(name="status", nullable = false)
    @Min(0)
    @Max(5)
    @JsonIgnore
    private Integer status;

    @Column(name="comments")
    @JsonProperty("comments")
    @JsonIgnore
    private String comments;

    // todo: Q: what is auth_code in Order? USER-KEY used when creating it?
    @Column(name="auth_code")
    @JsonIgnore
    private String authCode;

    // todo: Q: what is reference in Order? HTTP-REFERER header when creating it?
    @Column(name="reference")
    @JsonIgnore
    private String reference;

    @Column(name="shipping_id")
    @JsonIgnore
    private Long shippingId;

    @Column(name="tax_id")
    @JsonIgnore
    private Long taxId;

    Order() {
    }

    @Override
    public Long getId() {
        return this.id;
    }

    Long getCustomerId() {
        return customerId;
    }

    void setOrderItems(List<OrderItem> items) {
        this.orderItems = items;
    }

    void setCustomer(Customer customer) {
        if (customer.getId() == null)
            throw new IllegalArgumentException("Customer must have ID");
        this.customerId = customer.getId();
        this.customer = customer;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public Instant getCreatedOn() {
        return createdOn;
    }

    public Instant getShippedOn() {
        return shippedOn;
    }

    @JsonView(ViewProfile.Basic.class)
    @JsonProperty("name")
    public String getCustomerName() {
        return this.customer.getName();
    }

    void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    void setShippedOn(Instant shippedOn) {
        this.shippedOn = shippedOn;
    }

    void setStatus(Integer status) {
        this.status = status;
    }

    void setComments(String comments) {
        this.comments = comments;
    }

    void setShippingId(Long shippingId) {
        this.shippingId = shippingId;
    }

    void setTaxId(Long taxId) {
        this.taxId = taxId;
    }
}
