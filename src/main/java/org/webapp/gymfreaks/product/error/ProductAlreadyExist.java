package org.webapp.gymfreaks.product.error;

public class ProductAlreadyExist extends RuntimeException {

    public static final String PRODUCT_ALREADY_EXIST = "Product already exist";

    public ProductAlreadyExist(String message) {
        super(PRODUCT_ALREADY_EXIST + " " + message);
    }

    public ProductAlreadyExist() {
        super(PRODUCT_ALREADY_EXIST);
    }
}
