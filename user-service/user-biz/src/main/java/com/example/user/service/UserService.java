package com.example.user.service;

import com.example.user.dto.UserCreateRequest;
import com.example.user.dto.UserResponse;
import com.example.user.dto.UserUpdateRequest;

import java.util.List;

public interface UserService {

    UserResponse create(UserCreateRequest userCreateRequest);

    UserResponse update(String id, UserUpdateRequest userUpdateRequest);

    UserResponse getUserById(String id);

    UserResponse getUserByUsername(String username);

    List<UserResponse> getUsers();

    List<UserResponse> getUsersByRoleName(String roleName);

    void delete(String id);
}
