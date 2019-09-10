package ua.com.gavluk.turing.ecommerce.core;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.hibernate.annotations.Type;
import ua.com.gavluk.turing.ecommerce.api.ViewProfile;
import ua.com.gavluk.turing.ecommerce.utils.BigDecimalMoneySerializer;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name="shopping_cart")
@JsonInclude(JsonInclude.Include.NON_ABSENT)
@JsonPropertyOrder(
        {"item_id", "cart_id", "name", "attributes", "product_id", "image", "price", "discounted_price", "quantity", "subtotal"}
        )
public class ShoppingCartItem extends DbEntity {

    @Column(name="item_id", unique=true, nullable=false)
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @JsonProperty("item_id")
    @JsonView(ViewProfile.Minimal.class)
    private Long id;

    @Column(name="cart_id", nullable=false)
    @Type(type = "uuid-char")
    @JsonProperty("cart_id")
    @JsonView(ViewProfile.Minimal.class)
    private UUID cartId;

    @Column(name="product_id", nullable=false)
    @JsonProperty("product_id")
    @JsonView(ViewProfile.Minimal.class)
    private Long productId;

    @Column(name="attributes", nullable=false)
    @JsonProperty("attributes")
    @JsonView(ViewProfile.Minimal.class)
    private String attributesStr;

    @Column(name="quantity", nullable=false)
    @JsonProperty("quantity")
    @JsonView(ViewProfile.Minimal.class)
    private Integer quantity;

    @Column(name="buy_now", nullable=false)
    @JsonIgnore
    private Boolean buyNow;

    @Column(name="added_on", nullable=false)
    @JsonIgnore
    private Instant addedOn;

    @Transient
    @JsonIgnore
    private Product product;

    @Override
    public Long getId() {
        return this.id;
    }


    void setCartId(UUID cartId) {
        this.cartId = cartId;
    }

    void setProductId(Long productId) {
        this.productId = productId;
    }

    void setAttributesStr(String attributesStr) {
        this.attributesStr = attributesStr;
    }

    void setBuyNow(Boolean buyNow) {
        this.buyNow = buyNow;
    }

    void setAddedOn(Instant addedOn) {
        this.addedOn = addedOn;
    }

    void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public UUID getCartId() {
        return cartId;
    }

    public Long getProductId() {
        return productId;
    }

    public String getAttributesStr() {
        return attributesStr;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Boolean getBuyNow() {
        return buyNow;
    }

    public Instant getAddedOn() {
        return addedOn;
    }

    public Product getProduct() {
        return product;
    }

    void setProduct(Product product) {
        this.product = product;
    }

    @JsonProperty("name")
    public String getProductName() {
        return this.product == null ? null : this.product.getName();
    }

    @JsonProperty("image")
    public String getProductImage() {
        return this.product == null ? null : this.product.getMainImageFileName();
    }

    @JsonProperty("price")
    @JsonSerialize(using = BigDecimalMoneySerializer.class)
    public BigDecimal getProductPrice() {
        return this.product == null ? null : this.product.getPrice();
    }

    @JsonProperty("discounted_price")
    @JsonSerialize(using = BigDecimalMoneySerializer.class)
    public BigDecimal getProductDiscountedPrice() {
        return this.product == null ? null : this.product.getDiscountedPrice();
    }

    @JsonProperty("subtotal")
    @JsonSerialize(using = BigDecimalMoneySerializer.class)
    public BigDecimal getSubtotal() {
        return this.product == null ? null : this.getProductPrice().multiply(BigDecimal.valueOf(this.getQuantity()));
    }

    @Override
    public String toString() {
        return "ShoppingCartItem{" +
                "cartId=" + cartId +
                ", productId=" + productId +
                ", quantity=" + quantity +
                '}';
    }
}
