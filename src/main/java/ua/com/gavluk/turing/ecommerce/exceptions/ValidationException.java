package ua.com.gavluk.turing.ecommerce.exceptions;

import javax.validation.Payload;

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
    public static final Profile BAD_SHIPPING_REGION_ID   = new Profile(400, "USR_09", "The Shipping Region ID is not number");
    public static final Profile BAD_ID_NOT_NUMBER       = new Profile(400, "DEP_01", "The ID is not a number");

    public static final Profile BAD_PARAMETER           = new Profile(400, "COM_01", "Bad parameter");
    public static final Profile BAD_REQUEST             = new Profile(400, "COM_02", "Bad request");
    public static final Profile BAD_FACEBOOK_TOKEN      = new Profile(400, "USR_11", "Bad facebook token");
    public static final Profile SHOPPING_CART_IS_EMPTY = new Profile(400, "ORD_01", "Shopping cart is empty");
    public static final String VALIDATION_CONSTRAINT_MESSAGE_PREFIX = "VALIDATION";


    public ValidationException(Profile profile, String field) {
        super(profile, field);
    }

    public ValidationException(Profile profile) {
        super(profile);
    }


    public static class ProfileBuilder implements Payload {

        public Profile buildFromMessage(String message) {
            String[] msgSpit = message.split(":", 2);
            String code = msgSpit.length == 2 ? msgSpit[0] : "COM_01";
            return new Profile(400, code, msgSpit[msgSpit.length-1]);
        }
    }
}
