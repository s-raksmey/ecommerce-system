package com.example.business.domain.port.output;

import com.example.business.domain.entity.Business;

public interface BusinessRepository {
    Business saveBusiness(Business business);
}
