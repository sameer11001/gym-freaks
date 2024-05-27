package org.webapp.gymfreaks.core.error;

public class ResourceNotFoundException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = """
            Resource not found!
            """;

    public ResourceNotFoundException() {
        super();
    }

    public ResourceNotFoundException(String message) {
        super(DEFAULT_MESSAGE + " " + message);
    }

}
