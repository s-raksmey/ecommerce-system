package com.example.customer.client.fallback;

import com.example.customer.client.exception.UserServiceUnavailableException;
import feign.FeignException;
import feign.Request;
import feign.Response;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.CompletionException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class UserFeignClientFallbackFactoryTest {

    private final UserFeignClientFallbackFactory factory = new UserFeignClientFallbackFactory();

    @Test
    void preservesNotFoundResponse() {
        Request request = Request.create(
                Request.HttpMethod.GET,
                "/api/users/missing",
                Map.of(),
                null,
                StandardCharsets.UTF_8,
                null
        );
        FeignException notFound = FeignException.errorStatus(
                "UserFeignClient#getById",
                Response.builder()
                        .status(404)
                        .reason("Not Found")
                        .request(request)
                        .build()
        );

        assertThatThrownBy(() -> factory.create(new CompletionException(notFound)).getById("missing"))
                .isSameAs(notFound);
    }

    @Test
    void reportsUnavailableServiceWithOriginalCause() {
        RuntimeException cause = new RuntimeException("connection refused");

        assertThatThrownBy(() -> factory.create(cause).getById("user-1"))
                .isInstanceOf(UserServiceUnavailableException.class)
                .hasMessage("user-service is unavailable while loading user user-1")
                .hasCause(cause);
    }
}
