package com.example.customer.restapi.dto;

import java.util.UUID;

public record CustomerResponse(
        UUID id,
        String familyName,
        String givenName,
        String email,
        String phoneNumber
) {
}
