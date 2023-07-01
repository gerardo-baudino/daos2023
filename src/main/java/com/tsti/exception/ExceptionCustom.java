package com.tsti.exception;

import org.springframework.http.HttpStatus;

import java.io.Serial;

public class ExceptionCustom extends Exception {
    @Serial
    private static final long serialVersionUID = 3941221036411842318L;
    private String message;
    private HttpStatus statusCode;

    public ExceptionCustom() {
        super();

    }
    public ExceptionCustom(String message, HttpStatus statusCode) {
        super();
        this.message = message;
        this.statusCode = statusCode;
    }
    @Override
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    public HttpStatus getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(HttpStatus statusCode) {
        this.statusCode = statusCode;
    }
}
