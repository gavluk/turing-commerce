package ua.com.gavluk.turing.ecommerce.core;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

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
}
