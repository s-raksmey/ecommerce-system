package com.example.payment.domain.ports.output.repository;

import com.example.domain.valueobject.CustomerId;
import com.example.payment.domain.entity.CreditHistory;

import java.util.List;
import java.util.Optional;

public interface CreditHistoryRepository {

    CreditHistory save(CreditHistory creditHistory);

    Optional<List<CreditHistory>> findByCustomerId(CustomerId customerId);
}
