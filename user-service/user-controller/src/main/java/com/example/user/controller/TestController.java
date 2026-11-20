package com.example.user.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello Sentinel";
    }

    @GetMapping("/slow")
    @SentinelResource(value = "slowUser", fallback = "slowFallback", blockHandler = "slowBlocked")
    public String slow() throws InterruptedException {
        Thread.sleep(500);
        return "slow ok";
    }

    public String slowFallback(Throwable throwable) {
        return "fallback: " + throwable.getMessage();
    }

    public String slowBlocked(BlockException exception) {
        return "circuit OPEN";
    }
}
