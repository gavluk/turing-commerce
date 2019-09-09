package ua.com.gavluk.turing.ecommerce.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import ua.com.gavluk.turing.ecommerce.core.AttributeValue;

@JsonPropertyOrder({"attribute_name", "attribute_value_id", "attribute_value"})
public class AttributeValieInProductDTO {
    private final AttributeValue value;

    public AttributeValieInProductDTO(AttributeValue value) {
        this.value = value;
    }

    @JsonProperty("attribute_name")
    public String getAttributeName() {
        return value.getAttributeName();
    }

    @JsonProperty("attribute_value_id")
    public Long getAttributeValueId() {
        return this.value.getId();
    }

    @JsonProperty("attribute_value")
    public String getAttributeValue() {
        return this.value.getValue();
    }

}
