package ua.com.gavluk.turing.ecommerce.core;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import ua.com.gavluk.turing.ecommerce.api.ViewProfile;
import ua.com.gavluk.turing.ecommerce.utils.BigDecimalMoneySerializer;

import javax.persistence.*;
import java.math.BigDecimal;

import static java.util.Optional.ofNullable;

@Entity
@Table(name="order_detail")
@JsonView(ViewProfile.Minimal.class)
public class OrderItem extends DbEntity {

    @Column(name="item_id", unique=true, nullable=false)
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @Column(name="order_id", nullable=false)
    @JsonIgnore
    private Long orderId;

    @Column(name="product_id", nullable = false)
    @JsonProperty("product_id")
    private Long productId;

    @Column(name="attributes", nullable=false)
    @JsonProperty("attributes")
    private String attributesStr;

    @Column(name="product_name", nullable=false)
    @JsonProperty("product_name")
    private String productName;

    @Column(name="quantity", nullable=false)
    @JsonProperty("quantity")
    private Integer quantity;

    @Column(name="unit_cost", nullable=false)
    @JsonProperty("unit_cost")
    @JsonSerialize(using = BigDecimalMoneySerializer.class)
    private BigDecimal unitCost;

    // for hibernate
    OrderItem() {
    }

    OrderItem(Order order, ShoppingCartItem x, Product product) {
        this.orderId = ofNullable(ofNullable(order)
                .orElseThrow(()-> new IllegalArgumentException("order must be not null"))
                .getId())
                .orElseThrow(()->new IllegalArgumentException("order must have ID"));
        this.attributesStr = x.getAttributesStr();
        this.productId = ofNullable(ofNullable(product)
                .orElseThrow(()-> new IllegalArgumentException("product must be not null"))
                .getId())
                .orElseThrow(()->new IllegalArgumentException("product must have ID"));
        this.productName = product.getName();
        this.quantity = ofNullable(x)
                .orElseThrow(()->new IllegalArgumentException("shoping cart item must be not null"))
                .getQuantity();
        this.unitCost = product.getPrice();
    }

    @Override
    public Long getId() {
        return this.id;
    }

    public String getAttributesStr() {
        return attributesStr;
    }

    public String getProductName() {
        return productName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public BigDecimal getUnitCost() {
        return unitCost;
    }

    @JsonProperty("subtotal")
    @JsonSerialize(using = BigDecimalMoneySerializer.class)
    public BigDecimal getSubtotal() {
        return this.unitCost.multiply(BigDecimal.valueOf(this.getQuantity()));
    }

}
