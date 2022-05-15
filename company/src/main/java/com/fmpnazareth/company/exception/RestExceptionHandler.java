package com.fmpnazareth.company.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.xml.bind.ValidationException;

@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler{

    @ExceptionHandler(ValidationException.class)
    protected ResponseEntity<Object> handleValidationException(ValidationException exception){
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST,
                exception.getMessage(),
                exception);
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
}
