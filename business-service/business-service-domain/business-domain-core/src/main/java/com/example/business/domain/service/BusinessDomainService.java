package com.example.business.domain.service;

import com.example.business.domain.entity.Business;
import com.example.business.domain.event.OrderApprovalEvent;

import java.util.List;

public interface BusinessDomainService {
    OrderApprovalEvent validateOrder(Business business, List<String> failureMessages);
}
