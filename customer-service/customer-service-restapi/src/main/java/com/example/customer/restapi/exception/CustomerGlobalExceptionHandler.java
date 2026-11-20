package com.example.customer.restapi.exception;

import com.example.customer.client.exception.UserServiceUnavailableException;
import com.example.customer.domain.exception.CustomerDomainException;
import com.example.restapi.dto.RestApiErrorResponse;
import com.example.restapi.exception.GlobalExceptionHandler;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomerGlobalExceptionHandler extends GlobalExceptionHandler {

    @ExceptionHandler(UserServiceUnavailableException.class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public RestApiErrorResponse<?> handleUserServiceUnavailableException(
            UserServiceUnavailableException exception
    ) {
        return RestApiErrorResponse.builder()
                .code(HttpStatus.SERVICE_UNAVAILABLE.getReasonPhrase())
                .message(exception.getMessage())
                .build();
    }

    @ExceptionHandler(CustomerDomainException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public RestApiErrorResponse<?> handleCustomerDomainException(CustomerDomainException e) {
        return RestApiErrorResponse.builder()
                .code(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                .message(e.getMessage())
                .build();
    }
}
