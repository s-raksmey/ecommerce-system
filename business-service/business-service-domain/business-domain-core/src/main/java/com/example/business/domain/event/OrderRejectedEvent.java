package com.example.business.domain.event;

import com.example.business.domain.entity.OrderApproval;
import com.example.domain.valueobject.BusinessId;

import java.time.ZonedDateTime;
import java.util.List;

public class OrderRejectedEvent extends OrderApprovalEvent {
    public OrderRejectedEvent(OrderApproval orderApproval, BusinessId businessId, List<String> failureMessages, ZonedDateTime createdAt) {
        super(orderApproval, businessId, failureMessages, createdAt);
    }
}
