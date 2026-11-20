package com.example.gateway.handler;

import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.BlockRequestHandler;
import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.GatewayCallbackManager;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Map;

@Component
public class SentinelBlockHandler implements BlockRequestHandler {

    @PostConstruct
    public void register() {
        GatewayCallbackManager.setBlockHandler(this);
    }

    @Override
    public Mono<ServerResponse> handleRequest(ServerWebExchange exchange, Throwable exception) {
        if (exception instanceof DegradeException) {
            return response(HttpStatus.SERVICE_UNAVAILABLE, "Service unavailable");
        }
        return response(HttpStatus.TOO_MANY_REQUESTS, "Request count exceeds the maximum limit");
    }

    private Mono<ServerResponse> response(HttpStatus status, String message) {
        return ServerResponse.status(status)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(Map.of("code", status.value(), "message", message));
    }
}
