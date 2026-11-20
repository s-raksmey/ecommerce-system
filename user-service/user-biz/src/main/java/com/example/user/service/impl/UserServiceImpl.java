package com.example.user.service.impl;

import com.example.core.PageResponse;
import com.example.user.dto.UserCreateRequest;
import com.example.user.dto.UserResponse;
import com.example.user.dto.UserUpdateRequest;
import com.example.user.entity.UserEntity;
import com.example.user.exception.PasswordMismatchException;
import com.example.user.exception.UserHybridDataInconsistencyException;
import com.example.user.exception.UserNotFoundException;
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
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
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

    @Override
    public UserResponse getById(String userId) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(
                        "User not found with id " + userId
                ));
        UserRepresentation userRepresentation = keycloakUserService
                .getKeycloakUserById(userId);
        return userMapper.toResponse(userEntity, userRepresentation);
    }

    @Override
    public UserResponse update(String userId, UserUpdateRequest request) {
        UserEntity userEntity = findUser(userId);
        userMapper.updateEntity(request, userEntity);
        userRepository.save(userEntity);

        UserRepresentation userRepresentation = keycloakUserService.getKeycloakUserById(userId);
        if (request.familyName() != null) {
            userRepresentation.setFirstName(request.familyName());
        }
        if (request.givenName() != null) {
            userRepresentation.setLastName(request.givenName());
        }
        if (request.familyName() != null || request.givenName() != null) {
            keycloakUserService.updateKeycloakUser(userId, userRepresentation);
        }
        return userMapper.toResponse(userEntity, userRepresentation);
    }

    @Override
    public UserResponse getByUsername(String username) {
        UserEntity userEntity = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(
                        "User not found with username " + username
                ));
        UserRepresentation userRepresentation = keycloakUserService
                .getKeycloakUserByUsername(username);
        return userMapper.toResponse(userEntity, userRepresentation);
    }

    @Override
    public PageResponse<UserResponse> getUsersByRoleName(String roleName, Pageable pageable) {
        return mergeUsers(keycloakUserService.getKeycloakUsersByRoleName(roleName, pageable));
    }

    @Override
    public PageResponse<UserResponse> getUsers(Pageable pageable) {
        return mergeUsers(keycloakUserService.getKeycloakUsers(pageable));
    }

    @Override
    public void deleteById(String userId) {
        UserEntity userEntity = findUser(userId);
        keycloakUserService.deleteKeycloakUserById(userId);
        userRepository.delete(userEntity);
    }

    private UserEntity findUser(String userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(
                        "User not found with id " + userId
                ));
    }

    private PageResponse<UserResponse> mergeUsers(Slice<UserRepresentation> users) {
        List<UserResponse> items = users.stream()
                .map(userRepresentation -> {
                    UserEntity userEntity = userRepository.findById(userRepresentation.getId())
                            .orElseThrow(() -> new UserHybridDataInconsistencyException(
                                    "User exists in Keycloak but not in the application database"
                            ));
                    return userMapper.toResponse(userEntity, userRepresentation);
                })
                .toList();
        return PageResponse.<UserResponse>builder()
                .items(items)
                .page(users.getNumber())
                .size(users.getSize())
                .hasNext(users.hasNext())
                .build();
    }

}
