package com.example.payment.domain.exception;

import com.example.exception.DomainException;

public class PaymentDomainException extends DomainException {
    public PaymentDomainException(String message, Throwable cause) {
        super(message, cause);
    }

    public PaymentDomainException(String message) {
        super(message);
    }
}
