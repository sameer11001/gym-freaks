package org.webapp.gymfreaks.core.error;

public class OTPServiceException extends RuntimeException {

    public OTPServiceException(String message) {
        super(message);
    }

    public OTPServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public OTPServiceException(Throwable cause) {
        super(cause);
    }

}
