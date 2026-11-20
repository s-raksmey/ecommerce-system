package com.example.customer.client.fallback;

import com.example.customer.client.exception.UserServiceUnavailableException;
import com.example.customer.client.feign.UserFeignClient;
import feign.FeignException;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class UserFeignClientFallbackFactory implements FallbackFactory<UserFeignClient> {

    @Override
    public UserFeignClient create(Throwable cause) {
        return id -> {
            Throwable failure = rootCause(cause);
            if (failure instanceof FeignException.NotFound notFound) {
                throw notFound;
            }
            throw new UserServiceUnavailableException(
                    "user-service is unavailable while loading user " + id,
                    failure
            );
        };
    }

    private Throwable rootCause(Throwable cause) {
        Throwable current = cause;
        while (current.getCause() != null && current.getCause() != current) {
            current = current.getCause();
        }
        return current;
    }
}
