package ua.com.gavluk.turing.ecommerce.exceptions;

public class InternalErrorException extends CommonException {

    public static final Profile UNKNOWN_ERROR          = new Profile(500, "COM_02", "Unknown server error");

    public InternalErrorException(Profile profile) {
        super(profile);
    }
}
