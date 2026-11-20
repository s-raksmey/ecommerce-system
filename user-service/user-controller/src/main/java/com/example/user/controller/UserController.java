package com.example.user.controller;

import com.example.core.PageResponse;
import com.example.user.dto.UserCreateRequest;
import com.example.user.dto.UserResponse;
import com.example.user.dto.UserUpdateRequest;
import com.example.user.service.UserService;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public UserResponse create(@Valid @RequestBody UserCreateRequest userCreateRequest) {
        return userService.create(userCreateRequest);
    }

    @PatchMapping("/{id}")
    public UserResponse update(
            @PathVariable String id,
            @RequestBody UserUpdateRequest request
    ) {
        return userService.update(id, request);
    }

    @GetMapping("/{id}")
    public UserResponse getById(@PathVariable String id) {
        return userService.getById(id);
    }

    @GetMapping("/username/{username}")
    public UserResponse getByUsername(@PathVariable String username) {
        return userService.getByUsername(username);
    }

    @GetMapping("/roles/{roleName}")
    public PageResponse<UserResponse> getUsersByRoleName(
            @PathVariable String roleName,
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "15") int pageSize
    ) {
        return userService.getUsersByRoleName(
                roleName,
                PageRequest.of(pageNumber, pageSize)
        );
    }

    @SentinelResource(value = "getUsers", fallback = "getUsersFallback")
    @GetMapping
    public PageResponse<UserResponse> getUsers(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "15") int pageSize
    ) {
        return userService.getUsers(PageRequest.of(pageNumber, pageSize));
    }

    public PageResponse<UserResponse> getUsersFallback(
            int pageNumber,
            int pageSize,
            Throwable throwable
    ) {
        return PageResponse.<UserResponse>builder()
                .items(java.util.List.of())
                .page(pageNumber)
                .size(pageSize)
                .build();
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable String id) {
        userService.deleteById(id);
    }

}
