package com.project.garden.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.IOException;

@ResponseBody
@ControllerAdvice
@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class ErrorAdvice {

    @ExceptionHandler(Error.class)
    public Error errorHandler(Error e) {
        return e;
    }

    @ExceptionHandler(IOException.class)
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public Error errorIOHandler(Error e) {
        return e;
    }
}
