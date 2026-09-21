package com.example.restapi.dto;

public record FieldErrorResponse(
        String field,
        String code,
        String reason
) {
}
