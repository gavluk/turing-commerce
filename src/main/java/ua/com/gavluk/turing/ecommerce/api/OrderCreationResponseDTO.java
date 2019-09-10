package ua.com.gavluk.turing.ecommerce.api;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OrderCreationResponseDTO {

    @JsonProperty("order_id")
    private Long orderId;

    public OrderCreationResponseDTO(Long orderId) {
        this.orderId = orderId;
    }

    public Long getOrderId() {
        return orderId;
    }
}
