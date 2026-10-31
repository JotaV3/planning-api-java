package io.github.jotav3.planning_api.entities.controllers.handlers;

import io.github.jotav3.planning_api.entities.exceptions.ValidationException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class ControllerExceptionHandler {

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
}
