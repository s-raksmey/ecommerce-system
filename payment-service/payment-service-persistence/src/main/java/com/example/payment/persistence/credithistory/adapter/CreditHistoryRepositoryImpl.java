package com.example.payment.persistence.credithistory.adapter;

import com.example.domain.valueobject.CustomerId;
import com.example.payment.persistence.credithistory.entity.CreditHistoryEntity;
import com.example.payment.persistence.credithistory.mapper.CreditHistoryPersistenceMapper;
import com.example.payment.persistence.credithistory.repository.CreditHistoryJpaRepository;
import com.example.payment.domain.entity.CreditHistory;
import com.example.payment.domain.ports.output.repository.CreditHistoryRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class CreditHistoryRepositoryImpl implements CreditHistoryRepository {

    private final CreditHistoryJpaRepository creditHistoryJpaRepository;
    private final CreditHistoryPersistenceMapper creditHistoryPersistenceMapper;

    public CreditHistoryRepositoryImpl(CreditHistoryJpaRepository creditHistoryJpaRepository,
                                       CreditHistoryPersistenceMapper creditHistoryPersistenceMapper) {
        this.creditHistoryJpaRepository = creditHistoryJpaRepository;
        this.creditHistoryPersistenceMapper = creditHistoryPersistenceMapper;
    }

    @Override
    public CreditHistory save(CreditHistory creditHistory) {
        return creditHistoryPersistenceMapper.creditHistoryEntityToCreditHistory(creditHistoryJpaRepository
                .save(creditHistoryPersistenceMapper.creditHistoryToCreditHistoryEntity(creditHistory)));
    }

    @Override
    public Optional<List<CreditHistory>> findByCustomerId(CustomerId customerId) {
        Optional<List<CreditHistoryEntity>> creditHistory =
                creditHistoryJpaRepository.findByCustomerId(customerId.value());
        return creditHistory
                .map(creditHistoryList ->
                        creditHistoryList.stream()
                                .map(creditHistoryPersistenceMapper::creditHistoryEntityToCreditHistory)
                                .collect(Collectors.toList()));
    }
}
