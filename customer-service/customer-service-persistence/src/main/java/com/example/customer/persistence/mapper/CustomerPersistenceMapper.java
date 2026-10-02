package com.example.customer.persistence.mapper;

import com.example.customer.domain.entity.Customer;
import com.example.customer.persistence.entity.CustomerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CustomerPersistenceMapper {
    @Mapping(source = "id.value", target = "id")
    CustomerEntity customerToCustomerEntity(Customer customer);

    @Mapping(source = "id", target = "id.value")
    Customer customerEntityToCustomer(CustomerEntity customerEntity);
}
