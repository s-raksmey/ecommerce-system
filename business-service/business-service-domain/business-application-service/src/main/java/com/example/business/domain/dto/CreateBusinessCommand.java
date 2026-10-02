package com.example.business.domain.dto;

import java.util.List;

public record CreateBusinessCommand(
        boolean active,
        List<CommandProduct> products
) {
}
