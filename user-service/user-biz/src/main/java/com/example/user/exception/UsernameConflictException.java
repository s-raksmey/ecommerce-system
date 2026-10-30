package com.example.user.exception;

import com.example.restapi.exception.GlobalException;
import org.springframework.http.HttpStatus;

public class UsernameConflictException extends GlobalException {
    public UsernameConflictException(HttpStatus status, String message) {
        super(status, message);
    }
}
