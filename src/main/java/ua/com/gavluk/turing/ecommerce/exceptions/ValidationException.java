package ua.com.gavluk.turing.ecommerce.exceptions;

public class ValidationException extends CommonException {

    public static final Profile BAD_SORT_ORDER          = new Profile(400, "PAG_01", "The order is not matched");
    public static final Profile BAD_SORT_FIELD          = new Profile(400, "PAG_02", "The field of order is not allow sorting");
    public static final Profile BAD_EMAIL_OR_PASSWORD   = new Profile(400, "USR_01", "Email or Password is invalid");
    public static final Profile FIELD_IS_REQUIRED       = new Profile(400, "USR_02", "The field(s) are/is required");
    public static final Profile BAD_EMAIL               = new Profile(400, "USR_03", "The email is invalid");
    public static final Profile EMAIL_EXISTS            = new Profile(400, "USR_04", "The email already exists");
    public static final Profile EMAIL_NOT_EXISTS        = new Profile(400, "USR_05", "The email doesn't exist");
    public static final Profile BAD_PHONE               = new Profile(400, "USR_06", "this is an invalid phone number");
    public static final Profile FIELD_TOO_LONG          = new Profile(400, "USR_07", "this is too long <FIELD NAME>");
    public static final Profile BAD_CREDIT_CARD         = new Profile(400, "USR_08", "this is an invalid Credit Card");
    public static final Profile BAD_SHIPPIN_REGION_ID   = new Profile(400, "USR_09", "The Shipping Region ID is not number");
    public static final Profile BAD_ID_NOT_NUMBER       = new Profile(400, "DEP_01", "The ID is not a number");

    public static final Profile BAD_PARAMETER       = new Profile(400, "COM_01", "Bad parameter");


    public ValidationException(Profile profile, String field) {
        super(profile, field);
    }
}
