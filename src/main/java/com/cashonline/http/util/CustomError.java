package com.cashonline.http.util;

import org.springframework.http.HttpStatus;
import java.util.Arrays;
import java.util.List;

public class CustomError {

    private int statusCode;
    private String message;
    private List<String> errors;

    public CustomError(HttpStatus status, String message, List<String> errors) {
        this.statusCode = status.value();
        this.message = message;
        this.errors = errors;
    }

    public CustomError(HttpStatus status, String message, String error) {
        this.statusCode = status.value();
        this.message = message;
        errors = Arrays.asList(error);
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return message;
    }

    public List<String> getErrors() {
        return errors;
    }
}
