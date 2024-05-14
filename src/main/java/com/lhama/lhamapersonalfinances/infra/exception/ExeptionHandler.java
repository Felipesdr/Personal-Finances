package com.lhama.lhamapersonalfinances.infra.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExeptionHandler {

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity handleValidationException(ValidationException e){
        return ResponseEntity.badRequest().body(new ValidationExceptionDTO(e.getMessage()));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity handleError404() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity handleError400(MethodArgumentNotValidException e) {
        var errors = e.getFieldErrors();
        return ResponseEntity.badRequest().body(errors.stream().map(BeanValidationDTO::new));
    }

    public record BeanValidationDTO(String field, String message){
        public BeanValidationDTO(FieldError error){
            this(error.getField(), error.getDefaultMessage());
        }
    }
}
