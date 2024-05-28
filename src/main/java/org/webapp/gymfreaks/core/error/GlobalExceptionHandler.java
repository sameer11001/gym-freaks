package org.webapp.gymfreaks.core.error;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.webapp.gymfreaks.auth.error.AccessDeniedException;

import org.webapp.gymfreaks.auth.error.TokenNotFoundException;
import org.webapp.gymfreaks.auth.error.UserAlreadyExistException;
import org.webapp.gymfreaks.auth.error.UserNotFoundException;
import org.webapp.gymfreaks.product.error.ProductAlreadyExist;
import org.webapp.gymfreaks.product.error.ProductNotFoundException;
import org.springframework.validation.FieldError;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Bad Request
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach(
                error -> {
                    String fieldName = ((FieldError) error).getField();
                    String message = error.getDefaultMessage();
                    errors.put(fieldName, message);
                });

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);

    }

    // Bad Request
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UserNotFoundException.class)
    protected ResponseEntity<Object> handleUserNotFoundException(final UserNotFoundException ex) {

        List<String> errors = new ArrayList<>();
        errors.add(ex.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), 400, errors);
        return ResponseEntity.status(400).body(errorResponse);
    }

    // Bad Request
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(UserAlreadyExistException.class)
    protected ResponseEntity<Object> handleUserAlreadyExistException(final UserAlreadyExistException ex) {

        List<String> errors = new ArrayList<>();
        errors.add(ex.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), 409, errors);
        return ResponseEntity.status(409).body(errorResponse);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(TokenNotFoundException.class)
    protected ResponseEntity<Object> handleTokenNotFoundException(final TokenNotFoundException ex) {

        List<String> errors = new ArrayList<>();
        errors.add(ex.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), 404, errors);
        return ResponseEntity.status(404).body(errorResponse);
    }

    // Forbidden
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(AccessDeniedException.class)
    protected ResponseEntity<Object> handleAccessDeniedException(final AccessDeniedException ex) {
        List<String> errors = new ArrayList<>();
        errors.add(ex.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), 403, errors);
        return ResponseEntity.status(403).body(errorResponse);
    }

    // Bad Request
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(RuntimeException.class)
    protected ResponseEntity<Object> handleRuntimeException(final RuntimeException ex) {
        List<String> errors = new ArrayList<>();
        errors.add(ex.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), 500, errors);
        return ResponseEntity.status(500).body(errorResponse);
    }

    // Not Found
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NullItemException.class)
    protected ResponseEntity<Object> handleNullItemExceptionHandler(final NullItemException ex) {
        List<String> errors = new ArrayList<>();
        errors.add(ex.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), 404, errors);
        return ResponseEntity.status(404).body(errorResponse);
    }

    // Not Found
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ProductNotFoundException.class)
    protected ResponseEntity<Object> handleProductNotFoundException(final ProductNotFoundException ex) {

        List<String> errors = new ArrayList<>();
        errors.add(ex.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), 404, errors);
        return ResponseEntity.status(404).body(errorResponse);
    }

    // Conflict
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(ProductAlreadyExist.class)
    protected ResponseEntity<Object> handlProductAlreadyExistException(final ProductAlreadyExist ex) {

        List<String> errors = new ArrayList<>();
        errors.add(ex.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), 409, errors);
        return ResponseEntity.status(409).body(errorResponse);
    }

    // Not Accepted
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    protected ResponseEntity<Object> handleResourceNotFoundException(final ResourceNotFoundException ex) {
        List<String> errors = new ArrayList<>();
        errors.add(ex.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), 404, errors);
        return ResponseEntity.status(404).body(errorResponse);
    }

}
