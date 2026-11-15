package io.github.jotav3.planning_api.api.handlers;

import io.github.jotav3.planning_api.application.services.exceptions.ResourceNotFoundException;
import io.github.jotav3.planning_api.application.services.exceptions.UnauthorizedException;
import io.github.jotav3.planning_api.application.services.exceptions.ValidationException;
import io.github.jotav3.planning_api.application.services.validation.InvalidField;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.List;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<StandardError> handleUnauthorizedException(
            UnauthorizedException e,
            HttpServletRequest request)
    {
        HttpStatus status = HttpStatus.UNAUTHORIZED;

        StandardError standardError = new StandardError(
                status.value(),
                e.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(status).body(standardError);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<StandardError> handleResourceNotFoundException(
            ResourceNotFoundException e,
            HttpServletRequest request)
    {
        HttpStatus status = HttpStatus.NOT_FOUND;

        StandardError standardError = new StandardError(
                status.value(),
                e.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(status).body(standardError);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<FieldError> handleValidationException(
            ValidationException e,
            HttpServletRequest request)
    {
        HttpStatus status = HttpStatus.UNPROCESSABLE_CONTENT;

        FieldError fieldError = new FieldError(
                status.value(),
                e.getMessage(),
                request.getRequestURI(),
                e.getInvalidFieldsList()
        );

        return ResponseEntity.status(status).body(fieldError);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<FieldError> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException e,
            HttpServletRequest request)
    {
        HttpStatus status = HttpStatus.UNPROCESSABLE_CONTENT;

        List<InvalidField> invalidFieldList = e.getFieldErrors()
                .stream()
                .map(fieldError -> new InvalidField(fieldError.getField(), fieldError.getDefaultMessage()))
                .toList();

        FieldError fieldError = new FieldError(
                status.value(),
                e.getMessage(),
                request.getRequestURI(),
                invalidFieldList
        );

        return ResponseEntity.status(status).body(fieldError);
    }
}
