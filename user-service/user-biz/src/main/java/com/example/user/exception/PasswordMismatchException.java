package com.example.user.exception;

import com.example.restapi.exception.GlobalException;
import org.springframework.http.HttpStatus;

public class PasswordMismatchException extends GlobalException {
    public PasswordMismatchException(HttpStatus status, String message) {
        super(status, message);
    }
}
