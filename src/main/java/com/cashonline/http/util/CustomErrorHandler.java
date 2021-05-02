package com.cashonline.http.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import javax.validation.ConstraintViolationException;
import java.util.stream.Collectors;

@ControllerAdvice
public class CustomErrorHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<CustomError> handleConstraintViolationException(ConstraintViolationException exception) {
        CustomError customError = new CustomError(
                HttpStatus.BAD_REQUEST,
                exception.getMessage(),
                exception.getConstraintViolations().stream().map(ex -> ex.getPropertyPath().toString() + ":" + ex.getMessage()).collect(Collectors.toList()));
        return ResponseEntity.badRequest().body(customError);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomError> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        CustomError customError = new CustomError(
                HttpStatus.BAD_REQUEST,
                exception.getMessage(),
                exception.getFieldErrors().stream().map(err -> err.getField() + ":" + err.getDefaultMessage()).collect(Collectors.toList()));
        return ResponseEntity.badRequest().body(customError);
    }
}