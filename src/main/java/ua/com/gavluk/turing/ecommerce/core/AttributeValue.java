package ua.com.gavluk.turing.ecommerce.core;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="attribute_id", referencedColumnName = "attribute_id")
    private Attribute attribute;

    AttributeValue() {
    }

    public Long getId() {
        return id;
    }

    public String getValue() {
        return value;
    }

    @JsonIgnore
    public String getAttributeName() {
        return this.attribute.getName();
    }


    @Override
    public String toString() {
        return "AttributeValue{" +
                "id=" + id +
                ", value='" + value + '\'' +
                ", attributeName=" + this.getAttributeName() +
//                ", attributeId=" + this.getAttributeId() +
//                ", attributeName=" + this.getAttributeName() +
                '}';
    }
}
