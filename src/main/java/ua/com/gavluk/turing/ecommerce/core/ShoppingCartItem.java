package ua.com.gavluk.turing.ecommerce.core;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name="shopping_cart")
@JsonInclude(JsonInclude.Include.NON_ABSENT)
public class ShoppingCartItem extends DbEntity {

    @Column(name="item_id", unique=true, nullable=false)
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @JsonProperty("item_id")
    private Long id;

    @Column(name="cart_id", nullable=false)
    @JsonProperty("cart_id")
    private UUID cartId;

    @Column(name="product_id", nullable=false)
    @JsonProperty("product_id")
    private Long productId;

    @Column(name="attributes", nullable=false)
    @JsonProperty("attributes")
    private String attributesStr;

    @Column(name="quantity", nullable=false)
    @JsonProperty("quantity")
    private Integer quantity;

    @Column(name="buy_now", nullable=false)
    @JsonProperty("buy_now")
    private Boolean buyNow;

    @Column(name="added_on", nullable=false)
    @JsonProperty("added_on")
    private Instant addedOn;

    @Transient
    private Product product;

    @Override
    public Long getId() {
        return null;
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
}
