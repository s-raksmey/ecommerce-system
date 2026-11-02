package com.example.user.controller;

import com.example.restapi.dto.RestApiErrorResponse;
import com.example.restapi.exception.GlobalException;
import com.example.restapi.exception.GlobalExceptionHandler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UserGlobalExceptionHandler extends GlobalExceptionHandler {

    @ExceptionHandler(GlobalException.class)
    public ResponseEntity<RestApiErrorResponse<?>> handleGlobalException(
            GlobalException exception
    ) {
        RestApiErrorResponse<?> response = RestApiErrorResponse.builder()
                .code(exception.getStatus().getReasonPhrase())
                .message(exception.getMessage())
                .build();

        return ResponseEntity.status(exception.getStatus()).body(response);
    }
}
