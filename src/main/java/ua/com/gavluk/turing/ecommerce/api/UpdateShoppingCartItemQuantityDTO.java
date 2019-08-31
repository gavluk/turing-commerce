package ua.com.gavluk.turing.ecommerce.api;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class UpdateShoppingCartItemQuantityDTO {
    @NotNull @Positive Integer quantity;

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
