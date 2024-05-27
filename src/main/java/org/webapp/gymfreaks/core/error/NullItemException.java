package org.webapp.gymfreaks.core.error;

public class NullItemException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = """
            Null Item Exception!
            """;

    public NullItemException() {
        super();
    }

    public NullItemException(String message) {
        super(DEFAULT_MESSAGE + " " + message);
    }
}
