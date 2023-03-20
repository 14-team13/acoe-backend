package com.aluminium.acoe.dto;

import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;

public class ErrorDto {
    private final int status;
    private final String message;
    private List<FieldError> fieldErrors = new ArrayList<>();

    public ErrorDto(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public void addFieldError(String objectName, String path, String message) {
        FieldError error = new FieldError(objectName, path, message);
        fieldErrors.add(error);
    }

    public List<FieldError> getFieldErrors() {
        return fieldErrors;
    }
}