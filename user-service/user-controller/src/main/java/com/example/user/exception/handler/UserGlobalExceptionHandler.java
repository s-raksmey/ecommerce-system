package com.example.user.exception.handler;

import com.example.restapi.dto.RestApiErrorResponse;
import com.example.restapi.exception.GlobalException;
import com.example.restapi.exception.GlobalExceptionHandler;
import com.example.user.exception.KeycloakException;
import com.example.user.exception.PasswordMismatchException;
import com.example.user.exception.UserHybridDataInconsistencyException;
import com.example.user.exception.UserNotFoundException;
import com.example.user.exception.UsernameConflictException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UserGlobalExceptionHandler extends GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(KeycloakException.class)
    public RestApiErrorResponse<?> handle(KeycloakException exception) {
        return build(exception);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(PasswordMismatchException.class)
    public RestApiErrorResponse<?> handle(PasswordMismatchException exception) {
        return build(exception);
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(UsernameConflictException.class)
    public RestApiErrorResponse<?> handle(UsernameConflictException exception) {
        return build(exception);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UserNotFoundException.class)
    public RestApiErrorResponse<?> handle(UserNotFoundException exception) {
        return build(exception);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(UserHybridDataInconsistencyException.class)
    public RestApiErrorResponse<?> handle(UserHybridDataInconsistencyException exception) {
        return build(exception);
    }

    private RestApiErrorResponse<?> build(GlobalException exception) {
        return RestApiErrorResponse.builder()
                .code(exception.getStatus().getReasonPhrase())
                .message(exception.getMessage())
                .build();
    }
}
