package com.example.customer.client.feign;

import com.example.customer.client.dto.UserResponse;
import com.example.customer.client.fallback.UserFeignClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "user-service",
        path = "/api/users",
        fallback = UserFeignClientFallback.class
)
public interface UserFeignClient {
    @GetMapping("/{id}")
    UserResponse getById(@PathVariable("id") String id);
}
