package com.example.user.service;

import com.example.user.dto.UserCreateRequest;
import com.example.user.dto.UserResponse;

public interface UserService {

    UserResponse create(UserCreateRequest userCreateRequest);

}
