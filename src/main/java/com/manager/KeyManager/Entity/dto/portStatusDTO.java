package com.manager.KeyManager.Entity.dto;

import com.manager.KeyManager.roles.protocolStatus;

public record portStatusDTO(
        String port,
        protocolStatus status
) {
}
