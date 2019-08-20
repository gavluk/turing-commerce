package ua.com.gavluk.turing.ecommerce.core;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="attribute_value")
@JsonInclude(JsonInclude.Include.NON_ABSENT)
public class AttributeValue {

    @Column(name="attribute_value_id", unique=true, nullable=false)
    @Id
    @JsonProperty("attribute_value_id")
    private Long id;

    @Column(name="value", nullable=false)
    @JsonProperty("value")
    private String value;

    @Column(name="attribute_id", nullable = false)
    @JsonIgnore
    private Long attributeId;

    AttributeValue() {
    }

    public Long getId() {
        return id;
    }

    public String getValue() {
        return value;
    }

    public Long getAttributeId() {
        return attributeId;
    }
}
