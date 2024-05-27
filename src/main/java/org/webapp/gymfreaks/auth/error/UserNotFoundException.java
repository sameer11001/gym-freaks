package org.webapp.gymfreaks.auth.error;

public class UserNotFoundException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = """
            User Not Found!
            """;

    public UserNotFoundException(String message) {
        super(DEFAULT_MESSAGE + " " + message);
    }

    public UserNotFoundException() {
        super(DEFAULT_MESSAGE);
    }
}
