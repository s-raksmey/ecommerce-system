package com.example.customer.restapi.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record CustomerCreateRequest(
        @NotBlank
        String username,
        @NotBlank
        String familyName,
        @NotBlank
        String givenName
) {
}
