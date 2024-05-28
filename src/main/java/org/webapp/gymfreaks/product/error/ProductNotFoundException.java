package org.webapp.gymfreaks.product.error;

public class ProductNotFoundException extends RuntimeException {

    public static final String PRODUCT_NOT_FOUND = "Product not found";

    public ProductNotFoundException(String message) {
        super(PRODUCT_NOT_FOUND + " " + message);
    }

    public ProductNotFoundException() {
        super(PRODUCT_NOT_FOUND);
    }

}
