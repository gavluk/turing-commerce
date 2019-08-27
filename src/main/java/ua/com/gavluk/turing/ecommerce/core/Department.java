package ua.com.gavluk.turing.ecommerce.core;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="department")
@JsonInclude(JsonInclude.Include.NON_ABSENT)
public class Department extends DbEntity {

    @Column(name="department_id", unique=true, nullable=false)
    @Id
    @JsonProperty("department_id")
    private Long id;

    @Column(name="name", nullable=false)
    @JsonProperty("name")
    private String name;

    @Column(name="description", nullable=false)
    @JsonProperty("description")
    private String description;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
