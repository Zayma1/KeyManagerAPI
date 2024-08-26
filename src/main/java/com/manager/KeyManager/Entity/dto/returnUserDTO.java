package com.manager.KeyManager.Entity.dto;

public record returnUserDTO(
        int userID,
        String token,
        int role,

        String username,
        int biometricID,
        String allowedPorts

) {
}
