package ua.com.gavluk.turing.ecommerce.exceptions;

public class AuthException extends CommonException {

    public static final CommonException.Profile AUTH_CODE_IS_EMPTY = new CommonException.Profile(401, "AUT_01", "Authorization code is empty");
    public static final CommonException.Profile ACCESS_UNAUTHORIZED = new CommonException.Profile(403, "AUT_02", "Access Unauthorized");

    public AuthException(CommonException.Profile profile) {
        super(profile.message, profile.code, null, profile.status);
    }

}
