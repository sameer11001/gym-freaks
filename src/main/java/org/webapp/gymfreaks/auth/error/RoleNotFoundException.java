package org.webapp.gymfreaks.auth.error;

public class RoleNotFoundException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = """
            Role not found!
            """;

    public RoleNotFoundException(String message) {
        super(DEFAULT_MESSAGE + " " + message);
    }

    public RoleNotFoundException() {
        super(DEFAULT_MESSAGE);
    }
}
