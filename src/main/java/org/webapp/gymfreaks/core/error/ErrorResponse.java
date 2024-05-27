package org.webapp.gymfreaks.core.error;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponse {

    private String message;
    private int statusCode;
    private Boolean success;
    private LocalDateTime dateTime;
    private List<String> details;

    public ErrorResponse(String message, int statusCode, List<String> details) {
        super();
        this.message = message;
        this.details = details;
        this.success = Boolean.FALSE;
        this.statusCode = statusCode;
        this.dateTime = LocalDateTime.now();
    }

    public ErrorResponse() {
        super();
    }

}
