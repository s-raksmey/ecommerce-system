package com.example.order.persistence.adapter;

import com.example.order.domain.entity.Customer;
import com.example.order.domain.port.ouput.CustomerRepository;
import com.example.order.persistence.mapper.CustomerPersistenceMapper;
import com.example.order.persistence.repository.CustomerJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Driven adapter for {@link CustomerRepository}.
 * Loads a customer row and maps it to the domain {@link Customer} for the create-order check.
 */
@Repository
@RequiredArgsConstructor
public class CustomerRepositoryAdapter implements CustomerRepository {

    private final CustomerJpaRepository customerJpaRepository;
    private final CustomerPersistenceMapper customerPersistenceMapper;

    @Override
    public Optional<Customer> findCustomer(UUID customerId) {
        return customerJpaRepository.findById(customerId)
                .map(customerPersistenceMapper::customerEntityToCustomer);
    }

}
