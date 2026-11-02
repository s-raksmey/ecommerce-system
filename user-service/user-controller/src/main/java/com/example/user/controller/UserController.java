package com.example.user.controller;

import com.example.user.dto.UserCreateRequest;
import com.example.user.dto.UserResponse;
import com.example.user.dto.UserUpdateRequest;
import com.example.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PutMapping("/{id}")
    public UserResponse update(@PathVariable String id,
                               @Valid @RequestBody UserUpdateRequest userUpdateRequest) {
        return userService.update(id, userUpdateRequest);
    }

    @GetMapping("/{id}")
    public UserResponse getUserById(@PathVariable String id) {
        return userService.getUserById(id);
    }

    @GetMapping("/username/{username}")
    public UserResponse getUserByUsername(@PathVariable String username) {
        return userService.getUserByUsername(username);
    }

    @GetMapping
    public List<UserResponse> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("/role/{roleName}")
    public List<UserResponse> getUsersByRoleName(@PathVariable String roleName) {
        return userService.getUsersByRoleName(roleName);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        userService.delete(id);
    }

}
