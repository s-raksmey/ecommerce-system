package com.example.user.service;

public interface KeycloakRoleService {

    void assignRoles(String userId, String roleNameWithDelimiter);

}
