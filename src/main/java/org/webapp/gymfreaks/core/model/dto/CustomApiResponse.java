package org.webapp.gymfreaks.core.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@Builder
public class CustomApiResponse<T> {

    @Builder.Default
    private LocalDateTime time = LocalDateTime.now();

    private int httpStatus;

    private Boolean isSuccess;

    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T response;

    public static final CustomApiResponse<Void> SUCCESS = CustomApiResponse.<Void>builder()
            .httpStatus(HttpStatus.OK.value())
            .isSuccess(true)
            .build();

    public static <T> CustomApiResponse<T> successOf(final T response, final String message) {
        return CustomApiResponse.<T>builder()
                .httpStatus(HttpStatus.OK.value())
                .isSuccess(true)
                .message(message)
                .response(response)
                .build();
    }

    public static <T> CustomApiResponse<T> errorOf(final HttpStatus httpStatus, final String message) {
        return CustomApiResponse.<T>builder()
                .httpStatus(httpStatus.value())
                .isSuccess(false)
                .message(message) // Cast message to type T (flexible for different error messages)
                .build();
    }
}
// TODO handle exception in API response