package org.webapp.gymfreaks.auth.error;

public class AccessDeniedException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = """
            Access Denied!
            """;

    public AccessDeniedException(String message) {
        super(DEFAULT_MESSAGE + " " + message);
    }

    public AccessDeniedException() {
        super(DEFAULT_MESSAGE);
    }
}
