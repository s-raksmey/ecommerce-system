package com.example.payment.domain.entity;

import com.example.entity.AggregateRoot;
import com.example.payment.domain.exception.PaymentDomainException;
import com.example.valueobject.CustomerId;
import com.example.valueobject.Money;
import com.example.valueobject.OrderId;
import com.example.valueobject.PaymentId;
import com.example.valueobject.PaymentStatus;

import java.util.UUID;

public class Payment extends AggregateRoot<PaymentId> {
    private final OrderId orderId;
    private final CustomerId customerId;
    private final Money price;
    private PaymentStatus paymentStatus;

    private Payment(Builder builder) {
        setId(builder.id);
        orderId = builder.orderId;
        customerId = builder.customerId;
        price = builder.price;
        paymentStatus = builder.paymentStatus;
    }

    public void validatePayment() {
        if (orderId == null || orderId.value() == null) {
            throw new PaymentDomainException("Order id is required");
        }
        if (customerId == null || customerId.value() == null) {
            throw new PaymentDomainException("Customer id is required");
        }
        if (price == null || !price.isGreaterThanZero()) {
            throw new PaymentDomainException("Payment price must be greater than zero");
        }
    }

    public void initializePayment() {
        setId(new PaymentId(UUID.randomUUID()));
        paymentStatus = PaymentStatus.PENDING;
    }

    public void complete() {
        if (paymentStatus != PaymentStatus.PENDING) {
            throw new PaymentDomainException("Payment is not in correct state for complete operation");
        }
        paymentStatus = PaymentStatus.COMPLETED;
    }

    public OrderId getOrderId() {
        return orderId;
    }

    public CustomerId getCustomerId() {
        return customerId;
    }

    public Money getPrice() {
        return price;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private PaymentId id;
        private OrderId orderId;
        private CustomerId customerId;
        private Money price;
        private PaymentStatus paymentStatus;

        private Builder() {
        }

        public Builder id(PaymentId val) {
            id = val;
            return this;
        }

        public Builder orderId(OrderId val) {
            orderId = val;
            return this;
        }

        public Builder customerId(CustomerId val) {
            customerId = val;
            return this;
        }

        public Builder price(Money val) {
            price = val;
            return this;
        }

        public Builder paymentStatus(PaymentStatus val) {
            paymentStatus = val;
            return this;
        }

        public Payment build() {
            return new Payment(this);
        }
    }
}
