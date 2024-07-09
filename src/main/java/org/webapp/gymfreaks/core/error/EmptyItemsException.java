package org.webapp.gymfreaks.core.error;

public class EmptyItemsException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = """
            Null Item Exception!
            """;

    public EmptyItemsException() {
        super();
    }

    public EmptyItemsException(String message) {
        super(DEFAULT_MESSAGE + " " + message);
    }
}
