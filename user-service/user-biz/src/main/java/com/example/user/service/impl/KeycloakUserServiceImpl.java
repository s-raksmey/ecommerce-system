package com.example.user.service.impl;

import com.example.user.config.KeycloakAdminClientConfig;
import com.example.user.exception.KeycloakException;
import com.example.user.service.KeycloakUserService;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class KeycloakUserServiceImpl implements KeycloakUserService {

    private final Keycloak keycloak;

    @Override
    public void createKeycloakUser(UserRepresentation userRepresentation) {
        try (Response response = keycloak.realm(KeycloakAdminClientConfig.REALM_NAME)
                .users()
                .create(userRepresentation)) {
            if (response.getStatus() != HttpStatus.CREATED.value()) {
                throw new KeycloakException(
                        HttpStatus.valueOf(response.getStatus()),
                        "Create user in keycloak failed with status " + response.getStatus()
                );
            }
        }
    }

    @Override
    public UserRepresentation getKeycloakUserByUsername(String username) {
        return keycloak.realm(KeycloakAdminClientConfig.REALM_NAME)
                .users()
                .search(username, true)
                .stream()
                .findFirst()
                .orElseThrow(() -> new KeycloakException(
                        HttpStatus.NOT_FOUND,
                        "User in Keycloak not found"
                ));
    }

    @Override
    public UserRepresentation getKeycloakUserById(String userId) {
        try {
            return keycloak.realm(KeycloakAdminClientConfig.REALM_NAME)
                    .users()
                    .get(userId)
                    .toRepresentation();
        } catch (NotFoundException exception) {
            throw new KeycloakException(
                    HttpStatus.NOT_FOUND,
                    "User in Keycloak not found"
            );
        }
    }

    @Override
    public void updateKeycloakUser(String userId, UserRepresentation userRepresentation) {
        keycloak.realm(KeycloakAdminClientConfig.REALM_NAME)
                .users()
                .get(userId)
                .update(userRepresentation);
    }

    @Override
    public Slice<UserRepresentation> getKeycloakUsersByRoleName(
            String roleName,
            Pageable pageable
    ) {
        List<UserRepresentation> users = keycloak.realm(KeycloakAdminClientConfig.REALM_NAME)
                .roles()
                .get(roleName)
                .getUserMembers((int) pageable.getOffset(), pageable.getPageSize() + 1);
        return toSlice(users, pageable);
    }

    @Override
    public Slice<UserRepresentation> getKeycloakUsers(Pageable pageable) {
        List<UserRepresentation> users = keycloak.realm(KeycloakAdminClientConfig.REALM_NAME)
                .users()
                .list((int) pageable.getOffset(), pageable.getPageSize() + 1);
        return toSlice(users, pageable);
    }

    @Override
    public void deleteKeycloakUserById(String userId) {
        try (Response response = keycloak.realm(KeycloakAdminClientConfig.REALM_NAME)
                .users()
                .delete(userId)) {
            if (response.getStatus() != HttpStatus.NO_CONTENT.value()) {
                throw new KeycloakException(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        "Delete user in Keycloak failed with status " + response.getStatus()
                );
            }
        }
    }

    private Slice<UserRepresentation> toSlice(
            List<UserRepresentation> users,
            Pageable pageable
    ) {
        boolean hasNext = users.size() > pageable.getPageSize();
        List<UserRepresentation> content = hasNext
                ? users.subList(0, pageable.getPageSize())
                : users;
        return new SliceImpl<>(content, pageable, hasNext);
    }

}
