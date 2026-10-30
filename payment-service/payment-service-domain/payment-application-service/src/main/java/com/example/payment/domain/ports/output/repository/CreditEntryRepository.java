package com.example.payment.domain.ports.output.repository;

import com.example.domain.valueobject.CustomerId;
import com.example.payment.domain.entity.CreditEntry;

import java.util.Optional;

public interface CreditEntryRepository {

    CreditEntry save(CreditEntry creditEntry);

    Optional<CreditEntry> findByCustomerId(CustomerId customerId);
}
