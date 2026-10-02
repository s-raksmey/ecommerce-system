package com.example.business.domain.usecase;

import com.example.business.domain.dto.CreateBusinessCommand;
import com.example.business.domain.dto.CreateBusinessResult;
import com.example.business.domain.entity.Business;
import com.example.business.domain.entity.Product;
import com.example.business.domain.exception.BusinessDomainException;
import com.example.business.domain.port.output.BusinessRepository;
import com.example.business.domain.service.BusinessDomainService;
import com.example.valueobject.Money;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class CreateBusinessUseCase {

    private final BusinessDomainService businessDomainService;
    private final BusinessRepository businessRepository;

    @Transactional
    public CreateBusinessResult execute(CreateBusinessCommand createBusinessCommand) {
        log.info("executing CreateBusinessUseCase: {}", createBusinessCommand);

        List<Product> products = createBusinessCommand.products().stream()
                .map(commandProduct -> Product.builder()
                        .name(commandProduct.name())
                        .price(new Money(commandProduct.price()))
                        .build())
                .toList();

        Business business = Business.builder()
                .active(createBusinessCommand.active())
                .products(products)
                .build();

        businessDomainService.validateAndInitializeBusiness(business);

        Business savedBusiness = businessRepository.saveBusiness(business);
        if (savedBusiness == null) {
            throw new BusinessDomainException("Could not save business into database");
        }

        return new CreateBusinessResult(savedBusiness.getId().value());
    }
}
