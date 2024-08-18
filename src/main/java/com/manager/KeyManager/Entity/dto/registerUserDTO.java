package com.manager.KeyManager.Entity.dto;

import com.manager.KeyManager.roles.UserRoles;

public record registerUserDTO(
        String username,
        int biometricID,
        int role,

        int userID
) {
}
