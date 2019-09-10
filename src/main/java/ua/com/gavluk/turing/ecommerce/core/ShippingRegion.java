package ua.com.gavluk.turing.ecommerce.core;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ShippingRegion extends DbEntity {

    @Column(name="shipping_region_id", unique=true, nullable=false)
    @Id
    @JsonProperty("shipping_region_id")
    private Long id;

    @Column(name="shipping_region", nullable=false)
    @JsonProperty("shipping_region")
    private String shippingRegion;

    ShippingRegion() {
    }

    @Override
    public Long getId() {
        return this.id;
    }

    public String getShippingRegion() {
        return shippingRegion;
    }
}
