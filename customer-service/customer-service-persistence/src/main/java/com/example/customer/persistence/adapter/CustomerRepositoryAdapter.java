package com.example.customer.persistence.adapter;

import com.example.customer.domain.entity.Customer;
import com.example.customer.domain.port.output.CustomerRepository;
import com.example.customer.persistence.entity.CustomerEntity;
import com.example.customer.persistence.mapper.CustomerPersistenceMapper;
import com.example.customer.persistence.repository.CustomerJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CustomerRepositoryAdapter implements CustomerRepository {

    private final CustomerJpaRepository customerJpaRepository;
    private final CustomerPersistenceMapper customerPersistenceMapper;

    @Override
    public Customer saveCustomer(Customer customer) {
        CustomerEntity customerEntity = customerPersistenceMapper.customerToCustomerEntity(customer);
        CustomerEntity savedCustomerEntity = customerJpaRepository.save(customerEntity);
        return customerPersistenceMapper.customerEntityToCustomer(savedCustomerEntity);
    }
}
