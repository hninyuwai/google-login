package com.sample.google_login.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class GoogleTokenVerificationException extends RuntimeException{
    public GoogleTokenVerificationException(String message) {
        super(message);
    }
    public GoogleTokenVerificationException(String message, Throwable cause) {
        super(message, cause);
    }
}
