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

/**
 * Application service for the create-order use case.
 * <p>
 * The REST adapter calls {@link #execute(CreateOrderCommand)}. This class talks to
 * the domain through {@link OrderDomainService} and to infrastructure only through
 * output ports ({@link CustomerRepository}, {@link BusinessRepository}, {@link OrderRepository}).
 * It does not know about HTTP or JPA.
 * <p>
 * Flow: confirm the customer, load the business and its products, map the command
 * to an {@link Order}, validate and initiate that order, then save it.
 */
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

        log.info("Found business: {}", business);

        Order order = orderDomainMapper.createOrderCommandToOrder(createOrderCommand);
        log.info("Order price: {}", order.getPrice().getAmount());
        OrderCreatedEvent orderCreatedEvent = orderDomainService.validateAndInitiateOrder(order, business);
        log.info("Order created event: {}", orderCreatedEvent.getOrder().getId());

        Order savedOrder = orderRepository.saveOrder(order);
        if (savedOrder == null) {
            throw new OrderDomainException("Could not save order into database");
        }

        return new CreateOrderResult(savedOrder.getId().value());
    }

}
