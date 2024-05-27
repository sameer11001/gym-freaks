package org.webapp.gymfreaks.auth.error;

public class PasswordValidationException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = """
            Password is invalid!
            """;

    public PasswordValidationException(String message) {
        super(DEFAULT_MESSAGE + " " + message);
    }

    public PasswordValidationException() {
        super(DEFAULT_MESSAGE);
    }
}
