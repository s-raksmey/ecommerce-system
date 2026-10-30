package com.example.user.service.impl;

import com.example.user.dto.UserCreateRequest;
import com.example.user.dto.UserResponse;
import com.example.user.entity.UserEntity;
import com.example.user.exception.PasswordMismatchException;
import com.example.user.exception.UsernameConflictException;
import com.example.user.mapper.UserMapper;
import com.example.user.repository.UserRepository;
import com.example.user.service.KeycloakRoleService;
import com.example.user.service.KeycloakUserService;
import com.example.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    private final KeycloakUserService keycloakUserService;
    private final KeycloakRoleService keycloakRoleService;

    @Override
    public UserResponse create(UserCreateRequest userCreateRequest) {
        UserEntity userEntity = userMapper.toEntity(userCreateRequest);

        if (userRepository.existsByUsername(userCreateRequest.username())) {
            throw new UsernameConflictException(HttpStatus.CONFLICT,
                    "Username already exists");
        }

        if (!userCreateRequest.password().equals(userCreateRequest.confirmedPassword())) {
            throw new PasswordMismatchException(HttpStatus.BAD_REQUEST,
                    "Password does not match");
        }

        UserRepresentation userRepresentation = userMapper.toRepresentation(userCreateRequest);
        userRepresentation.setEnabled(true);
        userRepresentation.setEmailVerified(false);

        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
        credentialRepresentation.setValue(userCreateRequest.password());

        userRepresentation.setCredentials(List.of(credentialRepresentation));

        keycloakUserService.createKeycloakUser(userRepresentation);
        UserRepresentation createdUser = keycloakUserService.getKeycloakUserByUsername(
                userEntity.getUsername()
        );
        log.info("User created: {}", createdUser);
        keycloakRoleService.assignRoles(createdUser.getId(), userCreateRequest.roleName());

        userEntity.setId(createdUser.getId());
        userRepository.save(userEntity);

        return userMapper.toResponse(userEntity, createdUser);
    }

}
