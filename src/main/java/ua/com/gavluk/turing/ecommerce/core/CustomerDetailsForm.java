package ua.com.gavluk.turing.ecommerce.core;

import com.fasterxml.jackson.annotation.JsonProperty;
import ua.com.gavluk.turing.ecommerce.exceptions.ValidationException;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class CustomerDetailsForm {

    @NotNull
    @JsonProperty("name")
    private String name;

    @NotNull
    // todo: complete the message -> payload -> CommonException idea to involve spring @Valid in controller params
    @Email(message = "USR_03:The email is invalid", payload = {ValidationException.ProfileBuilder.class})
    @JsonProperty("email")
    private String email;

    @JsonProperty("day_phone")
    // inspired by: https://stackoverflow.com/questions/42104546/java-regular-expressions-to-validate-phone-numbers/42105140
    @Pattern(regexp = "\\d{10}|(?:\\d{3}-){2}\\d{4}|\\(\\d{3}\\)\\d{3}-?\\d{4}", message = "USR_06:this is an invalid phone number")
    private String dayPhone;

    @JsonProperty("eve_phone")
    // inspired by: https://stackoverflow.com/questions/42104546/java-regular-expressions-to-validate-phone-numbers/42105140
    @Pattern(regexp = "\\d{10}|(?:\\d{3}-){2}\\d{4}|\\(\\d{3}\\)\\d{3}-?\\d{4}", message = "USR_06:this is an invalid phone number")
    private String evePhone;

    @JsonProperty("mob_phone")
    // inspired by: https://stackoverflow.com/questions/42104546/java-regular-expressions-to-validate-phone-numbers/42105140
    @Pattern(regexp = "\\d{10}|(?:\\d{3}-){2}\\d{4}|\\(\\d{3}\\)\\d{3}-?\\d{4}", message = "USR_06:this is an invalid phone number")
    private String mobPhone;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDayPhone() {
        return dayPhone;
    }

    public void setDayPhone(String dayPhone) {
        this.dayPhone = dayPhone;
    }

    public String getEvePhone() {
        return evePhone;
    }

    public void setEvePhone(String evePhone) {
        this.evePhone = evePhone;
    }

    public String getMobPhone() {
        return mobPhone;
    }

    public void setMobPhone(String mobPhone) {
        this.mobPhone = mobPhone;
    }
}
