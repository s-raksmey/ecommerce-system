package com.example.restapi.dto;

import lombok.Builder;

@Builder
public record RestApiErrorResponse<T>(
        String code,
        String message,
        T detail
) {
}
