package com.example.customer.restapi.controller;

import com.example.customer.domain.dto.CreateCustomerCommand;
import com.example.customer.domain.dto.CreateCustomerResult;
import com.example.customer.domain.usecase.CreateCustomerUseCase;
import com.example.customer.restapi.dto.CustomerCreateRequest;
import com.example.customer.restapi.dto.CustomerCreateResponse;
import com.example.customer.restapi.mapper.CustomerWebMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerCommandController {

    private final CreateCustomerUseCase createCustomerUseCase;
    private final CustomerWebMapper customerWebMapper;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public CustomerCreateResponse createCustomer(
            @Valid @RequestBody CustomerCreateRequest customerCreateRequest
    ) {
        CreateCustomerCommand createCustomerCommand =
                customerWebMapper.customerCreateRequestToCreateCustomerCommand(customerCreateRequest);
        CreateCustomerResult createCustomerResult = createCustomerUseCase.execute(createCustomerCommand);
        return customerWebMapper.createCustomerResultToCustomerCreateResponse(createCustomerResult);
    }
}
