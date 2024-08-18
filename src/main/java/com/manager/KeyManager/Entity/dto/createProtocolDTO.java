package com.manager.KeyManager.Entity.dto;

import com.manager.KeyManager.roles.protocolStatus;

import java.time.LocalDateTime;

public record createProtocolDTO(
        int protocolID,
        int userBiometric,
        String date,
        String time,

        String status
) {
}
