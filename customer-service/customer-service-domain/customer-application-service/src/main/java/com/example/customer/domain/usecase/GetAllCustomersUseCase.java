package com.example.customer.domain.usecase;

import com.example.core.PageResponse;
import com.example.customer.domain.dto.CustomerResult;
import com.example.customer.domain.dto.GetCustomersQuery;
import com.example.customer.domain.port.output.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class GetAllCustomersUseCase {

    private final CustomerRepository customerRepository;

    public PageResponse<CustomerResult> execute(GetCustomersQuery query) {
        log.info("Executing GetAllCustomersUseCase: {}", query);
        return customerRepository.findAll(query.page(), query.size());
    }
}
