package com.example.customer.restapi.controller;

import com.example.core.PageResponse;
import com.example.customer.client.dto.UserResponse;
import com.example.customer.client.feign.UserFeignClient;
import com.example.customer.domain.dto.CustomerResult;
import com.example.customer.domain.dto.GetCustomersQuery;
import com.example.customer.domain.usecase.GetAllCustomersUseCase;
import com.example.customer.restapi.dto.CustomerResponse;
import com.example.customer.restapi.mapper.CustomerWebMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerQueryController {

    private final UserFeignClient userFeignClient;
    private final GetAllCustomersUseCase getAllCustomersUseCase;
    private final CustomerWebMapper customerWebMapper;

    @GetMapping
    public PageResponse<CustomerResponse> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "25") int size
    ) {
        PageResponse<CustomerResult> result = getAllCustomersUseCase.execute(
                new GetCustomersQuery(page, size)
        );
        return customerWebMapper.toCustomerResponse(result);
    }

    @GetMapping("/check-user/{userId}")
    public UserResponse checkUser(@PathVariable String userId) {
        return userFeignClient.getById(userId);
    }
}
