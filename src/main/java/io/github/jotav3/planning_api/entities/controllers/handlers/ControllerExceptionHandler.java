package io.github.jotav3.planning_api.entities.controllers.handlers;

import io.github.jotav3.planning_api.entities.exceptions.ResourceNotFoundException;
import io.github.jotav3.planning_api.entities.exceptions.ValidationException;
import io.github.jotav3.planning_api.entities.validation.InvalidField;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.lang.reflect.Field;
import java.time.Instant;
import java.util.List;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<StandardError> handleResourceNotFoundException(ResourceNotFoundException e, HttpServletRequest request){
        HttpStatus status = HttpStatus.NOT_FOUND;

        StandardError standardError = new StandardError(
                Instant.now(),
                status.value(),
                e.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(status).body(standardError);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<FieldError> handleValidationException(ValidationException e, HttpServletRequest request){
        HttpStatus status = HttpStatus.UNPROCESSABLE_CONTENT;

        FieldError fieldError = new FieldError(
                Instant.now(),
                status.value(),
                e.getMessage(),
                request.getRequestURI(),
                e.getInvalidFieldsList()
        );

        return ResponseEntity.status(status).body(fieldError);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<FieldError> handleMethodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest request){
        HttpStatus status = HttpStatus.UNPROCESSABLE_CONTENT;

        List<InvalidField> invalidFieldList = e.getFieldErrors()
                .stream()
                .map(fieldError -> new InvalidField(fieldError.getField(), fieldError.getDefaultMessage()))
                .toList();

        FieldError fieldError = new FieldError(
                Instant.now(),
                status.value(),
                e.getMessage(),
                request.getRequestURI(),
                invalidFieldList
        );

        return ResponseEntity.status(status).body(fieldError);
    }
}
