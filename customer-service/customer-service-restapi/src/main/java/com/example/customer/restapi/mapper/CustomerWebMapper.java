package com.example.customer.restapi.mapper;

import com.example.customer.domain.dto.CreateCustomerCommand;
import com.example.customer.domain.dto.CreateCustomerResult;
import com.example.customer.restapi.dto.CustomerCreateRequest;
import com.example.customer.restapi.dto.CustomerCreateResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerWebMapper {
    CreateCustomerCommand customerCreateRequestToCreateCustomerCommand(CustomerCreateRequest customerCreateRequest);

    CustomerCreateResponse createCustomerResultToCustomerCreateResponse(CreateCustomerResult createCustomerResult);
}
