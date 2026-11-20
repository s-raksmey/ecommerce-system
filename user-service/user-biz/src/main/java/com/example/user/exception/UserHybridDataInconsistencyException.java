package com.example.user.exception;

import com.example.restapi.exception.GlobalException;
import org.springframework.http.HttpStatus;

public class UserHybridDataInconsistencyException extends GlobalException {

    public UserHybridDataInconsistencyException(String message) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, message);
    }
}
