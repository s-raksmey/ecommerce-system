package com.example.user.service.impl;

import com.example.user.config.KeycloakAdminClientConfig;
import com.example.user.service.KeycloakRoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class KeycloakRoleServiceImpl implements KeycloakRoleService {

    private final Keycloak keycloak;

    @Override
    public void assignRoles(String userId, String roleNameWithDelimiter) {
        List<RoleRepresentation> roleRepresentationList = Arrays.stream(roleNameWithDelimiter.split(","))
                .map(String::trim)
                .filter(roleName -> !roleName.isEmpty())
                .map(this::getKeycloakRoleByName)
                .toList();

        keycloak.realm(KeycloakAdminClientConfig.REALM_NAME)
                .users()
                .get(userId)
                .roles()
                .realmLevel()
                .add(roleRepresentationList);
    }

    @Override
    public List<UserRepresentation> getKeycloakUsersByRoleName(String roleName) {
        return keycloak.realm(KeycloakAdminClientConfig.REALM_NAME)
                .roles()
                .get(roleName)
                .getUserMembers();
    }

    private RoleRepresentation getKeycloakRoleByName(String roleName) {
        return keycloak.realm(KeycloakAdminClientConfig.REALM_NAME)
                .roles()
                .get(roleName)
                .toRepresentation();
    }

}
