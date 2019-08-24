package ua.com.gavluk.turing.ecommerce.exceptions;

public class AuthException extends CommonException {

    public static final CommonException.Profile AUTH_CODE_IS_EMPTY = new CommonException.Profile(401, "AUT_01", "Authorization code is empty");
    public static final CommonException.Profile ACCESS_UNAUTHORIZED = new CommonException.Profile(403, "AUT_02", "Access Unauthorized");
    public static final CommonException.Profile INVALID_AUTH_TOKEN = new CommonException.Profile(403, "AUT_03", "Authorization code is invalid");
    public static final CommonException.Profile EXPIRED_AUTH_TOKEN = new CommonException.Profile(403, "AUT_04", "Authorization code is expired");

    public AuthException(CommonException.Profile profile) {
        super(profile.message, profile.code, null, profile.status);
    }

    public AuthException(Profile profile, String field) {
        super(profile, field);
    }
}
