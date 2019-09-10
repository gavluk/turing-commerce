package ua.com.gavluk.turing.ecommerce.core;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
public class Shipping extends DbEntity {

    @Column(name="shipping_id", unique=true, nullable=false)
    @Id
    @JsonProperty("shipping_id")
    private Long id;

    @Column(name="shipping_type", nullable=false)
    @JsonProperty("shipping_type")
    private String shippingType;

    @Column(name="shipping_cost", nullable=false)
    @JsonProperty("shipping_cost")
    private BigDecimal shippingCost;

    @Column(name="shipping_region_id", nullable=false)
    @JsonProperty("shipping_region_id")
    private Long regionId;

    @Override
    public Long getId() {
        return this.id;
    }

    public String getShippingType() {
        return shippingType;
    }

    public BigDecimal getShippingCost() {
        return shippingCost;
    }

    public Long getRegionId() {
        return regionId;
    }
}
