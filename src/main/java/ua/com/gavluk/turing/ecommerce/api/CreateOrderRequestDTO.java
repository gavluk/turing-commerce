package ua.com.gavluk.turing.ecommerce.api;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.util.UUID;

public class CreateOrderRequestDTO {

    @NotBlank
    @JsonProperty("cart_id")
    private UUID cartId;

    @Positive
    @JsonProperty("shipping_id")
    private Long shippingId;

    @Positive
    @JsonProperty("tax_id")
    private Long taxId;

    public UUID getCartId() {
        return cartId;
    }

    public void setCartId(UUID cartId) {
        this.cartId = cartId;
    }

    public Long getShippingId() {
        return shippingId;
    }

    public void setShippingId(Long shippingId) {
        this.shippingId = shippingId;
    }

    public Long getTaxId() {
        return taxId;
    }

    public void setTaxId(Long taxId) {
        this.taxId = taxId;
    }
}
