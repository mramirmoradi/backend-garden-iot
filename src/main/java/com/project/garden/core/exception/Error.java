package com.project.garden.core.exception;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Error extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private int status;
    private String target;
    private String message;
    private List<FieldError> fieldErrors;

    public Error(String message) {
        super();
        this.message = message;
    }

    @Override
    @JsonIgnore
    public StackTraceElement[] getStackTrace() {
        return super.getStackTrace();
    }

    @Override
    @JsonIgnore
    public String getLocalizedMessage() {
        return super.getLocalizedMessage();
    }

    @Override
    @JsonIgnore
    public synchronized Throwable getCause() {
        return super.getCause();
    }
}
