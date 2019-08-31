package ua.com.gavluk.turing.ecommerce.api;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.UUID;

public class AddProductToCartDTO {

    @NotNull
    @JsonProperty("cart_id")
    private UUID cartId;

    @NotNull
    @Positive
    @JsonProperty("product_id")
    private Long productId;

    @JsonProperty("attributes")
    private String attributes;

    @NotNull
    @Positive
    @JsonProperty("quantity")
    private Integer quantity;

    public UUID getCartId() {
        return cartId;
    }

    public void setCartId(UUID cartId) {
        this.cartId = cartId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getAttributes() {
        return attributes;
    }

    public void setAttributes(String attributes) {
        this.attributes = attributes;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
