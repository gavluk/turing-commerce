package ua.com.gavluk.turing.ecommerce.exceptions;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_ABSENT)
@JsonIgnoreProperties({"stackTrace", "cause", "localizedMessage", "suppressed"})
@JsonPropertyOrder({"status", "code", "message", "field"})
public abstract class CommonException extends Exception {

    private final String code;
    private final String field;
    private final int status;

    public CommonException(Profile profile, String field) {
        super(profile.message);
        this.code = profile.code;
        this.status = profile.status;
        this.field = field;
    }

    public CommonException(Profile profile) {
        super(profile.message);
        this.code = profile.code;
        this.status = profile.status;
        this.field = null;
    }

    public CommonException(String message, String code, String field, int status) {
        super(message);
        this.code = code;
        this.field = field;
        this.status = status;
    }

    public CommonException(String message, String code, String field, int status, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.field = field;
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public String getField() {
        return field;
    }

    public int getStatus() {
        return status;
    }

    public static class Profile {
        final int status;
        final String code;
        final String message;

        public Profile(int status, String code, String message) {
            this.status = status;
            this.code = code;
            this.message = message;
        }
    }

}
