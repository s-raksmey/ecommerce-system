package com.example.payment.persistence.entity;

import com.example.valueobject.PaymentStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "payments")
public class PaymentEntity {
    @Id
    private UUID id;
    private UUID orderId;
    private UUID customerId;
    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;
}
