package com.example.customer.restapi.mapper;

import com.example.customer.domain.dto.CreateCustomerCommand;
import com.example.customer.domain.dto.CreateCustomerResult;
import com.example.customer.domain.dto.UpdateCustomerCommand;
import com.example.customer.domain.dto.UpdateCustomerResult;
import com.example.customer.restapi.dto.CustomerCreateRequest;
import com.example.customer.restapi.dto.CustomerCreateResponse;
import com.example.customer.restapi.dto.CustomerUpdateRequest;
import com.example.customer.restapi.dto.CustomerUpdateResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface CustomerWebMapper {
    CreateCustomerCommand customerCreateRequestToCreateCustomerCommand(CustomerCreateRequest customerCreateRequest);
    CustomerCreateResponse createCustomerResultToCustomerCreateResponse(CreateCustomerResult createCustomerResult);

    @Mapping(source = "customerId", target = "customerId")
    UpdateCustomerCommand customerUpdateRequestToUpdateCustomerCommand(UUID customerId, CustomerUpdateRequest customerUpdateRequest);
    CustomerUpdateResponse updateCustomerResultToCustomerUpdateResponse(UpdateCustomerResult updateCustomerResult);
}
