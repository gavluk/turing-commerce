package ua.com.gavluk.turing.ecommerce.core;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ProductReviewForm {

    // todo: Q: ask why we need this field here, we already have productId as path-URL var, just ignoring
    private Long productId;

    // todo: Q: ask product owner for maximum "relevant" review, now just as big as mysql "text"
    @Size(min=1, max=65535)
    private String review;

    @Min(1)
    @Max(5)
    private Integer rating;

    public ProductReviewForm(
            @JsonProperty("product_id") Long productId,
            @JsonProperty("review") @Size(min = 1, max = 65535) String review,
            @JsonProperty("rating") @Min(1) @Max(5) Integer rating
    ) {
        this.productId = productId;
        this.review = review;
        this.rating = rating;
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
}
