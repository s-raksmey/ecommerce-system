package com.example.customer.client.fallback;

import com.example.customer.client.dto.UserResponse;
import com.example.customer.client.exception.UserServiceUnavailableException;
import com.example.customer.client.feign.UserFeignClient;
import org.springframework.stereotype.Component;

@Component
public class UserFeignClientFallback implements UserFeignClient {

    @Override
    public UserResponse getById(String id) {
        throw new UserServiceUnavailableException(
                "user-service is unavailable while loading user " + id
        );
    }
}
