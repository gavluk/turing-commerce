package ua.com.gavluk.turing.ecommerce.core;

public class PaymentStatus {

    private final String id;
    private final boolean succeeded;
    protected String errorKey;
    protected String errorMessage;

    public PaymentStatus(String id, Boolean succeeded) {
        this.id = id;
        this.succeeded = succeeded;
        this.errorKey = null;
        this.errorMessage = null;
    }

    public PaymentStatus(String id, String errorKey, String errorMessage) {
        this.id = id;
        this.succeeded = false;
        this.errorKey = errorKey;
        this.errorMessage = errorMessage;
    }

    public String getId() {
        return id;
    }

    public boolean isSucceeded() {
        return succeeded;
    }
    public String getErrorKey() {
        return errorKey;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{" +
                "succeeded=" + succeeded +
                ", errorKey='" + errorKey + '\'' +
                '}';
    }
}
