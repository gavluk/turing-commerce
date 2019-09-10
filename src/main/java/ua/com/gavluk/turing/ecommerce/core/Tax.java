package ua.com.gavluk.turing.ecommerce.core;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name="tax")
@JsonInclude(JsonInclude.Include.NON_ABSENT)
public class Tax extends DbEntity {

    @Column(name="tax_id", unique=true, nullable=false)
    @Id
    @JsonProperty("tax_id")
    private Long id;

    @Column(name="tax_type", nullable=false)
    @JsonProperty("tax_type")
    private String taxType;

    @Column(name="tax_percentage", nullable=false)
    @JsonProperty("tax_percentage")
    private BigDecimal taxPercentage;

    @Override
    public Long getId() {
        return this.id;
    }

    // for deserialization issues only
    Tax() {
    }

    public String getTaxType() {
        return taxType;
    }

    public BigDecimal getTaxPercentage() {
        return taxPercentage;
    }
}
