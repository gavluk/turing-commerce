package ua.com.gavluk.turing.ecommerce.core;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.Email;

@Entity
@Table(name="customer")
//@JsonInclude(JsonInclude.Include.NON_ABSENT)
public class Customer {

    @Column(name="customer_id", unique=true, nullable=false)
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @JsonProperty("customer_id")
    protected Long id;

    @Column(name="name", nullable=false)
    @JsonProperty("name")
    private String name;

    @Email
    @Column(name="email", unique=true, nullable=false)
    @JsonProperty("email")
    private String email;

    @Column(name="password", nullable=false)
    @JsonIgnore
    private String password;

    @Column(name="credit_card")
    @JsonProperty("credit_card")
    private String creditCard;

    @Column(name="address_1")
    @JsonProperty("address_1")
    private String address1;

    @Column(name="address_2")
    @JsonProperty("address_2")
    private String address2;

    @Column(name="city")
    @JsonProperty("city")
    private String city;

    @Column(name="region")
    @JsonProperty("region")
    private String region;

    @Column(name="postal_code")
    @JsonProperty("postal_code")
    private String postalCode;

    @Column(name="country")
    @JsonProperty("country")
    private String country;

    @Column(name="shipping_region_id", nullable=false)
    @JsonProperty("shipping_region_id")
    private Long shippingRegionId = 1L;

    @Column(name="day_phone")
    @JsonProperty("day_phone")
    private String dayPhone;

    @Column(name="eve_phone")
    @JsonProperty("eve_phone")
    private String evePhone;

    @Column(name="mob_phone")
    @JsonProperty("mob_phone")
    private String mobPhone;

    Customer() {
    }

    Customer(String name, @Email String email, String encodedPassword) {
        this.name = name;
        this.email = email;
        this.password = encodedPassword;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    String getPassword() {
        return password;
    }

    public String getCreditCard() {
        return creditCard;
    }

    public String getAddress1() {
        return address1;
    }

    public String getAddress2() {
        return address2;
    }

    public String getCity() {
        return city;
    }

    public String getRegion() {
        return region;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getCountry() {
        return country;
    }

    public Long getShippingRegionId() {
        return shippingRegionId;
    }

    public String getDayPhone() {
        return dayPhone;
    }

    public String getEvePhone() {
        return evePhone;
    }

    public String getMobPhone() {
        return mobPhone;
    }

    void setName(String name) {
        this.name = name;
    }

    void setEmail(String email) {
        this.email = email;
    }

    void setDayPhone(String dayPhone) {
        this.dayPhone = dayPhone;
    }

    void setEvePhone(String evePhone) {
        this.evePhone = evePhone;
    }

    void setMobPhone(String mobPhone) {
        this.mobPhone = mobPhone;
    }

    void setAddress1(String address1) {
        this.address1 = address1;
    }

    void setAddress2(String address2) {
        this.address2 = address2;
    }

    void setCity(String city) {
        this.city = city;
    }

    void setRegion(String region) {
        this.region = region;
    }

    void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    void setCountry(String country) {
        this.country = country;
    }

    void setShippingRegionId(Long shippingRegionId) {
        this.shippingRegionId = shippingRegionId;
    }
}
