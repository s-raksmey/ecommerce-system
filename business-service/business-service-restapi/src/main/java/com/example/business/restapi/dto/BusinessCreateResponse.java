package com.example.business.restapi.dto;

import lombok.Builder;

import java.util.UUID;

@Builder
public record BusinessCreateResponse(
        UUID businessId
) {
}
