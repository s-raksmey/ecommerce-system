package com.example.business.domain.exception;

import com.example.exception.DomainException;

public class BusinessDomainException extends DomainException {
    public BusinessDomainException(String message, Throwable cause) {
        super(message, cause);
    }

    public BusinessDomainException(String message) {
        super(message);
    }
}
