package ua.com.gavluk.turing.ecommerce.exceptions;

public class NotFoundException extends CommonException {

    public static final Profile CATEGORY_NOT_FOUND = new Profile(404, "CAT_01", "Don't exist category with this ID");
    public static final Profile DEPARTMENT_NOT_FOUND = new Profile(404, "DEP_02", "Don'exist department with this ID");
    public static final Profile PRODUCT_NOT_FOUND = new Profile(404, "PRD_01", "Don'exist product with this ID");
    public static final Profile ATTRIBUTE_NOT_FOUND = new Profile(404, "ATR_01", "Don'exist attribute with this ID");

    public NotFoundException(Profile profile) {
        super(profile);
    }
    public NotFoundException(Profile profile, String field) {
        super(profile, field);
    }
}
