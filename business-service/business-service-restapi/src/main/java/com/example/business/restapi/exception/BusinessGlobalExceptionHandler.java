package com.example.business.restapi.exception;

import com.example.business.domain.exception.BusinessDomainException;
import com.example.restapi.dto.FieldErrorResponse;
import com.example.restapi.dto.RestApiErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class BusinessGlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public RestApiErrorResponse<?> handleValidationException(MethodArgumentNotValidException exception) {
        return RestApiErrorResponse.builder()
                .code(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message("Data validation failed")
                .detail(extractFieldErrors(exception.getFieldErrors()))
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public RestApiErrorResponse<?> handleUnreadableMessage(HttpMessageNotReadableException exception) {
        return RestApiErrorResponse.builder()
                .code(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message("Invalid request body. Check JSON format and field types.")
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BusinessDomainException.class)
    public RestApiErrorResponse<?> handleBusinessDomainException(BusinessDomainException exception) {
        return RestApiErrorResponse.builder()
                .code(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message(exception.getMessage())
                .build();
    }

    private List<FieldErrorResponse> extractFieldErrors(List<FieldError> fieldErrors) {
        return fieldErrors.stream()
                .map(fieldError -> new FieldErrorResponse(
                        fieldError.getField(),
                        fieldError.getCode(),
                        fieldError.getDefaultMessage()
                ))
                .toList();
    }
}
