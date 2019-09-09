package ua.com.gavluk.turing.ecommerce.core;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="attribute")
@JsonInclude(JsonInclude.Include.NON_ABSENT)
public class Attribute {

    @Column(name="attribute_id", unique=true, nullable=false)
    @Id
    @JsonProperty("attribute_id")
    private Long id;

    @Column(name="name", nullable=false)
    @JsonProperty("name")
    private String name;

/*
    @OneToMany(mappedBy = "attribute")
    private List<AttributeValue> attributeValues;
*/

    Attribute() {
    }

    Attribute(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Attribute{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
