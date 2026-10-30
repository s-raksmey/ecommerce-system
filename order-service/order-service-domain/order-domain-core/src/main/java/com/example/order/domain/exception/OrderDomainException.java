package com.example.order.domain.exception;

import com.example.domain.exception.DomainException;

public class OrderDomainException extends DomainException {

    public OrderDomainException(String message, Throwable cause) {
        super(message, cause);
    }

    public OrderDomainException(String message) {
        super(message);
    }

}
