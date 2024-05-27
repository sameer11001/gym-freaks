package org.webapp.gymfreaks.auth.error;

public class UserAlreadyExistException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = """
            User Already Exist!
            """;

    public UserAlreadyExistException(String message) {
        super(DEFAULT_MESSAGE + " " + message);
    }

    public UserAlreadyExistException() {
        super(DEFAULT_MESSAGE);
    }
}
