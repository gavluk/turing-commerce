package ua.com.gavluk.turing.ecommerce.core;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name="review")
@JsonInclude(JsonInclude.Include.NON_ABSENT)
public class ProductReview extends DbEntity {

    @Column(name="review_id", unique=true, nullable=false)
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @Column(name="customer_id", nullable=false)
    @JsonIgnore
    private Long customerId;

    @Column(name="product_id", nullable=false)
    @JsonIgnore
    private Long productId;

    @Column(name="review", nullable=false)
    @JsonProperty("review")
    private String review;

    // todo: Q: what is real rating diapason? might be enum?
    @Column(name="rating", nullable=false)
    @JsonProperty("rating")
    private Integer rating;

    @Column(name="created_on", nullable=false)
    @JsonProperty("created_on")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Instant createdOn;

    ProductReview() {
    }

    ProductReview(Long customerId, Long productId, String review, Integer rating, Instant createdOn) {
        this.customerId = customerId;
        this.productId = productId;
        this.review = review;
        this.rating = rating;
        this.createdOn = createdOn;
    }

    public Long getId() {
        return id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public Long getProductId() {
        return productId;
    }

    public String getReview() {
        return review;
    }

    public Integer getRating() {
        return rating;
    }

    public Instant getCreatedOn() {
        return createdOn;
    }
}
