package com.backend.backend.security.exceptions;

import org.springframework.http.HttpStatus;

public class CustomException extends RuntimeException{

    private HttpStatus status;
    private int customErrorCode;


    public CustomException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
    public int getCustomErrorCode() {
        return customErrorCode;
    }
}
