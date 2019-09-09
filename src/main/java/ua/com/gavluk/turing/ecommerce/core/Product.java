package ua.com.gavluk.turing.ecommerce.core;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import ua.com.gavluk.turing.ecommerce.api.ViewProfile;
import ua.com.gavluk.turing.ecommerce.utils.BigDecimalMoneySerializer;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name="product")
@JsonInclude(JsonInclude.Include.NON_ABSENT)
public class Product extends DbEntity {

    @Column(name="product_id", unique=true, nullable=false)
    @Id
    @JsonProperty("product_id")
    @JsonView(ViewProfile.Basic.class)
    private Long id;

    @Column(name="name", nullable=false)
    @JsonProperty("name")
    @JsonView(ViewProfile.Basic.class)
    private String name;

    @Column(name="description", nullable=false)
    @JsonProperty("description")
    @JsonView(ViewProfile.Basic.class)
    private String description;

    @Column(name="price", nullable=false)
    @JsonProperty("price")
    @JsonSerialize(using = BigDecimalMoneySerializer.class)
    @JsonView(ViewProfile.Basic.class)
    private BigDecimal price;

    @Column(name="discounted_price", nullable=false)
    @JsonProperty("discounted_price")
    @JsonSerialize(using = BigDecimalMoneySerializer.class)
    @JsonView(ViewProfile.Basic.class)
    private BigDecimal discountedPrice;

    @Column(name="image")
    @JsonProperty("image")
    @JsonView(ViewProfile.Full.class)
    private String mainImageFileName;

    @Column(name="image_2")
    @JsonProperty("image_2")
    @JsonView(ViewProfile.Full.class)
    private String secondaryImageFileName;

    @Column(name="thumbnail")
    @JsonProperty("thumbnail")
    @JsonView(ViewProfile.Basic.class)
    private String thumbnailFileName;

    @Column(name="display")
    @JsonProperty("display")
    @JsonView(ViewProfile.Full.class)
    // todo: Q: what is 'display'? some enum about how to display? In example it is [0,1,2,3]... convert to enum?
    private Integer display;


    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name="product_attribute",
            joinColumns =
                    @JoinColumn(name = "product_id"),
            inverseJoinColumns =
                    @JoinColumn(name = "attribute_value_id")
    )
    private List<AttributeValue> attributeValues;

    @ManyToMany
    @JoinTable(
            name="product_category",
            joinColumns =
                    @JoinColumn(name = "product_id"),
            inverseJoinColumns =
                    @JoinColumn(name = "category_id")
    )
    private List<Category> categories;

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

    @JsonIgnore
    /**
     * It could be called only if used findById() service method (it uses lazy initialization from DB)
     */
    public List<AttributeValue> getAttributeValues() {
        return attributeValues;
    }

    public void truncateDescriptionTo(Integer descriptionLength) {
        if (this.description.length() > descriptionLength)
            this.description = this.description.substring(0, descriptionLength) + "...";
    }
}
