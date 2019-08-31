package ua.com.gavluk.turing.ecommerce.exceptions;

public class NotFoundException extends CommonException {

    public static final Profile CATEGORY_NOT_FOUND = new Profile(404, "CAT_01", "Don't exist category with this ID");
    public static final Profile DEPARTMENT_NOT_FOUND = new Profile(404, "DEP_02", "Don'exist department with this ID");
    public static final Profile PRODUCT_NOT_FOUND = new Profile(404, "PRD_01", "Don'exist product with this ID");
    public static final Profile ATTRIBUTE_NOT_FOUND = new Profile(404, "ATR_01", "Don'exist attribute with this ID");
    public static final Profile CUSTOMER_NOT_FOUND = new Profile(404, "CST_01", "Don'exist customer with this ID");
    public static final Profile CART_ITEM_NOT_FOUND = new Profile(404, "ITM_01", "Don'exist cart item with this ID");
    public static final Profile TAX_NOT_FOUND = new Profile(404, "TAX_01", "Don'exist tax with this ID");
    public static final Profile SHIPPING_REGION_NOT_FOUND = new Profile(404, "SHP_01", "Don'exist shipping region with this ID");
    public static final Profile ORDER_NOT_FOUND = new Profile(404, "ORD_01", "Don'exist order with this ID");

    public NotFoundException(Profile profile) {
        super(profile);
    }
    public NotFoundException(Profile profile, String field) {
        super(profile, field);
    }
}
