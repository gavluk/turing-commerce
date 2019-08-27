package ua.com.gavluk.turing.ecommerce.core;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import ua.com.gavluk.turing.ecommerce.utils.BigDecimalMoneySerializer;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name="product")
@JsonInclude(JsonInclude.Include.NON_ABSENT)
public class Product extends DbEntity {

    @Column(name="product_id", unique=true, nullable=false)
    @Id
    @JsonProperty("product_id")
    private Long id;

    @Column(name="name", nullable=false)
    @JsonProperty("name")
    private String name;

    @Column(name="description", nullable=false)
    @JsonProperty("description")
    private String description;

    @Column(name="price", nullable=false)
    @JsonProperty("price")
    @JsonSerialize(using = BigDecimalMoneySerializer.class)
    private BigDecimal price;

    @Column(name="discounted_price", nullable=false)
    @JsonProperty("discounted_price")
    @JsonSerialize(using = BigDecimalMoneySerializer.class)
    private BigDecimal discountedPrice;

    @Column(name="image")
    @JsonProperty("image")
    private String mainImageFileName;

    @Column(name="image_2")
    @JsonProperty("image_2")
    private String secondaryImageFileName;

    @Column(name="thumbnail")
    @JsonProperty("thumbnail")
    private String thumbnailFileName;

    @Column(name="display")
    @JsonProperty("display")
    // todo: what is 'display'? some enum about how to display? In example it is [0,1,2,3]... convert to enum?
    private Integer display;

    /**
     * just for Hibernate serialization
     */
    Product() {
    }

    /**
     * Non-public constructor for serialization only
     */
    Product(
            Long id,
            String name,
            String description,
            BigDecimal price,
            BigDecimal discountedPrice,
            String mainImageFileName,
            String secondaryImageFileName,
            String thumbnailFileName,
            Integer display
    ) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.discountedPrice = discountedPrice;
        this.mainImageFileName = mainImageFileName;
        this.secondaryImageFileName = secondaryImageFileName;
        this.thumbnailFileName = thumbnailFileName;
        this.display = display;
    }

    @Override
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public BigDecimal getDiscountedPrice() {
        return discountedPrice;
    }

    public String getMainImageFileName() {
        return mainImageFileName;
    }

    public String getSecondaryImageFileName() {
        return secondaryImageFileName;
    }

    public String getThumbnailFileName() {
        return thumbnailFileName;
    }

    public Integer getDisplay() {
        return display;
    }

    public void truncateDescriptionTo(Integer descriptionLength) {
        if (this.description.length() > descriptionLength)
            this.description = this.description.substring(0, descriptionLength) + "...";
    }
}
