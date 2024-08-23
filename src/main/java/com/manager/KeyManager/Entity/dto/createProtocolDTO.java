package com.manager.KeyManager.Entity.dto;

import com.manager.KeyManager.roles.protocolStatus;

import java.time.LocalDateTime;

public record createProtocolDTO(
        int protocolID,
        int userBiometric,
        int port,
        String initialDate,
        String initialTime,
        String Returndate,
        String Returntime,

        String status
) {
}
