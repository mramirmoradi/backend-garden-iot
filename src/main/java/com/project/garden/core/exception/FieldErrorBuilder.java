package com.project.garden.core.exception;

import java.util.ArrayList;
import java.util.List;

public class FieldErrorBuilder {

    public static List<FieldError> map(List<org.springframework.validation.FieldError> fieldErrors) {
        List<FieldError> errors = new ArrayList<>();
        fieldErrors.forEach(fieldError -> {
            FieldError error = new FieldError();
            error.setName(fieldError.getField());
            error.setValue(fieldError.getRejectedValue());
            error.setReason(fieldError.getDefaultMessage());
            errors.add(error);
        });
        return errors;
    }
}
