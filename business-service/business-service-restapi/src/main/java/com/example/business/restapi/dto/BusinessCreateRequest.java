package com.example.business.restapi.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.List;

@Builder
public record BusinessCreateRequest(
        @NotNull
        Boolean active,
        @NotEmpty
        @Valid
        List<ProductRequest> products
) {
}
