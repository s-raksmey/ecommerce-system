package com.example.user.service;

import org.keycloak.representations.idm.UserRepresentation;

public interface KeycloakUserService {

    void createKeycloakUser(UserRepresentation userRepresentation);

    UserRepresentation getKeycloakUserByUsername(String username);

    UserRepresentation getKeycloakUserById(String id);

    void updateKeycloakUser(String id, UserRepresentation userRepresentation);

    void deleteKeycloakUser(String id);
}
