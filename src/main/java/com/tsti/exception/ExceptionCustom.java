package com.tsti.exception;

public class ExceptionCustom extends Exception {
    private static final long serialVersionUID = 3941221036411842318L;
    private String message;
    private int statusCode;

    public ExceptionCustom() {
        super();

    }
    public ExceptionCustom(String message, int statusCode) {
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

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}
