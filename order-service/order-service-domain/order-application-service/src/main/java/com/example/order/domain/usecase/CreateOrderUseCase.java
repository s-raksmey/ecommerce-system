package com.example.order.domain.usecase;

import com.example.order.domain.dto.CreateOrderCommand;
import com.example.order.domain.dto.CreateOrderResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
public class CreateOrderUseCase {

    public CreateOrderResult execute(CreateOrderCommand createOrderCommand) {
        log.info("executing CreateOrderUseCase: {}", createOrderCommand);
        return new CreateOrderResult(UUID.randomUUID());
    }

}
