package org.webapp.gymfreaks.product.error;

public class CategoryNotFound extends RuntimeException{

    private static final String DEFAULT_MESSAGE = """
            Category Not Found
            """;

    public CategoryNotFound() {
        super(DEFAULT_MESSAGE);
    }

    public CategoryNotFound(String message) {
        super(DEFAULT_MESSAGE + " " + message);
    }


}
