package org.webapp.gymfreaks.core.error;

public class RunTimeException extends RuntimeException{

    private static final String DEFAULT_MESSAGE = """
            Run Time Exception!
            """;
    public RunTimeException(String message) {
        super(DEFAULT_MESSAGE + " " + message);
    }

    public RunTimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public RunTimeException(Throwable cause) {
        super(cause);
    }

    public RunTimeException() {
        super(DEFAULT_MESSAGE);
    }
}
