package ua.com.gavluk.turing.ecommerce.core;

public class PaymentStatus {

    private final String id;
    private final boolean succeed;
    protected String errorKey;
    protected String errorMessage;

    public PaymentStatus(String id, Boolean succeed) {
        this.id = id;
        this.succeed = succeed;
        this.errorKey = null;
        this.errorMessage = null;
    }

    public PaymentStatus(String id, String errorKey, String errorMessage) {
        this.id = id;
        this.succeed = false;
        this.errorKey = errorKey;
        this.errorMessage = errorMessage;
    }

    public String getId() {
        return id;
    }

    public boolean isSucceed() {
        return succeed;
    }
    public String getErrorKey() {
        return errorKey;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
