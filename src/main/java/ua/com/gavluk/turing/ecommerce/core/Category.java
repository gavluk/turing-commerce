package ua.com.gavluk.turing.ecommerce.core;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="category")
@JsonInclude(JsonInclude.Include.NON_ABSENT)
public class Category {

    @Column(name="category_id", unique=true, nullable=false)
    @Id
    @JsonProperty("category_id")
    private Long id;

    @Column(name="name", nullable=false)
    @JsonProperty("name")
    private String name;

    @Column(name="description", nullable=false)
    @JsonProperty("description")
    private String description;

    @Column(name="department_id", nullable=false)
    @JsonProperty("department_id")
    private String departmentId;

    /**
     * Hibernate serialization
     */
    Category() {
    }

    Category(Long id, String name, String description, String departmentId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.departmentId = departmentId;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getDepartmentId() {
        return departmentId;
    }
}
