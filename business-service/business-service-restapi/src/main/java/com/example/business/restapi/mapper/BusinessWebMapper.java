package com.example.business.restapi.mapper;

import com.example.business.domain.dto.CreateBusinessCommand;
import com.example.business.domain.dto.CreateBusinessResult;
import com.example.business.restapi.dto.BusinessCreateRequest;
import com.example.business.restapi.dto.BusinessCreateResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BusinessWebMapper {
    CreateBusinessCommand businessCreateRequestToCreateBusinessCommand(BusinessCreateRequest businessCreateRequest);

    BusinessCreateResponse createBusinessResultToBusinessCreateResponse(CreateBusinessResult createBusinessResult);
}
