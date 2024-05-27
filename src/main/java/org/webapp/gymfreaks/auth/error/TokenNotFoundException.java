package org.webapp.gymfreaks.auth.error;

public class TokenNotFoundException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = """
            User Not Found!
            """;

    public TokenNotFoundException(String message) {
        super(DEFAULT_MESSAGE + " " + message);
    }

    public TokenNotFoundException() {
        super(DEFAULT_MESSAGE);
    }
}
