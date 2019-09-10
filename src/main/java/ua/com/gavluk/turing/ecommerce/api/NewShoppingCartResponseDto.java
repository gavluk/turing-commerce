package ua.com.gavluk.turing.ecommerce.api;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class NewShoppingCartResponseDto {

    @JsonProperty("cart_id")
    private final UUID cartId;

    public NewShoppingCartResponseDto(UUID brandNewShoppingCartId) {
        this.cartId = brandNewShoppingCartId;
    }

    public UUID getCartId() {
        return cartId;
    }
}
