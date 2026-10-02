package com.example.business.restapi.controller;

import com.example.business.domain.dto.CreateBusinessCommand;
import com.example.business.domain.dto.CreateBusinessResult;
import com.example.business.domain.usecase.CreateBusinessUseCase;
import com.example.business.restapi.dto.BusinessCreateRequest;
import com.example.business.restapi.dto.BusinessCreateResponse;
import com.example.business.restapi.mapper.BusinessWebMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/businesses")
@RequiredArgsConstructor
public class BusinessCommandController {

    private final CreateBusinessUseCase createBusinessUseCase;
    private final BusinessWebMapper businessWebMapper;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public BusinessCreateResponse createBusiness(
            @Valid @RequestBody BusinessCreateRequest businessCreateRequest
    ) {
        CreateBusinessCommand createBusinessCommand =
                businessWebMapper.businessCreateRequestToCreateBusinessCommand(businessCreateRequest);
        CreateBusinessResult createBusinessResult = createBusinessUseCase.execute(createBusinessCommand);
        return businessWebMapper.createBusinessResultToBusinessCreateResponse(createBusinessResult);
    }
}
