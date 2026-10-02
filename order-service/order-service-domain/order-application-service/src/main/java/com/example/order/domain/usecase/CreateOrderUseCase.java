package com.example.order.domain.usecase;

import com.example.order.domain.dto.CreateOrderCommand;
import com.example.order.domain.dto.CreateOrderResult;
import com.example.order.domain.entity.Business;
import com.example.order.domain.entity.Order;
import com.example.order.domain.entity.Product;
import com.example.order.domain.event.OrderCreatedEvent;
import com.example.order.domain.exception.OrderDomainException;
import com.example.order.domain.mapper.OrderDomainMapper;
import com.example.order.domain.port.ouput.BusinessRepository;
import com.example.order.domain.port.ouput.CustomerRepository;
import com.example.order.domain.port.ouput.OrderRepository;
import com.example.order.domain.service.OrderDomainService;
import com.example.valueobject.BusinessId;
import com.example.valueobject.Money;
import com.example.valueobject.ProductId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class CreateOrderUseCase {

    private final OrderDomainService orderDomainService;
    private final OrderDomainMapper orderDomainMapper;
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final BusinessRepository businessRepository;

    @Transactional
    public CreateOrderResult execute(CreateOrderCommand createOrderCommand) {
        log.info("executing CreateOrderUseCase: {}", createOrderCommand);

        customerRepository.findCustomer(createOrderCommand.customerId())
                .orElseThrow(() -> new OrderDomainException(
                        "Could not find customer with ID: " + createOrderCommand.customerId()
                ));

        List<Product> products = createOrderCommand.items().stream()
                .map(commandOrderItem -> Product.builder()
                        .id(new ProductId(commandOrderItem.productId()))
                        .name("product")
                        .price(new Money(commandOrderItem.price()))
                        .build())
                .toList();

        Business business = Business.builder()
                .id(new BusinessId(createOrderCommand.businessId()))
                .products(products)
                .build();

        business = businessRepository.findBusiness(business)
                .orElseThrow(() -> new OrderDomainException(
                        "Could not find business with ID: " + createOrderCommand.businessId()
                ));

        Order order = orderDomainMapper.createOrderCommandToOrder(createOrderCommand);
        OrderCreatedEvent orderCreatedEvent = orderDomainService.validateAndInitiateOrder(order, business);
        log.info("Order created event: {}", orderCreatedEvent);

        Order savedOrder = orderRepository.saveOrder(orderCreatedEvent.getOrder());
        if (savedOrder == null) {
            throw new OrderDomainException("Could not save order");
        }

        return new CreateOrderResult(savedOrder.getId().value());
    }

}
