package com.example.user.service;

import com.example.core.PageResponse;
import com.example.user.dto.UserCreateRequest;
import com.example.user.dto.UserResponse;
import com.example.user.dto.UserUpdateRequest;
import org.springframework.data.domain.Pageable;

public interface UserService {

    UserResponse create(UserCreateRequest userCreateRequest);

    UserResponse getById(String userId);

    UserResponse update(String userId, UserUpdateRequest request);

    UserResponse getByUsername(String username);

    PageResponse<UserResponse> getUsersByRoleName(String roleName, Pageable pageable);

    PageResponse<UserResponse> getUsers(Pageable pageable);

    void deleteById(String userId);

}
