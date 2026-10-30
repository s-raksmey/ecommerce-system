package com.example.customer.restapi.dto;

import java.util.UUID;

public record CustomerUpdateResponse (
        UUID customerId
) {
}
