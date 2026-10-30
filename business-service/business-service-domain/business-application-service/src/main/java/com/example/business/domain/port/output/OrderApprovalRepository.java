package com.example.business.domain.port.output;

import com.example.business.domain.entity.OrderApproval;

public interface OrderApprovalRepository {

    OrderApproval save(OrderApproval orderApproval);

}
