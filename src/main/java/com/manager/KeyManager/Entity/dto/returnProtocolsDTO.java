package com.manager.KeyManager.Entity.dto;

import com.manager.KeyManager.Entity.users;
import com.manager.KeyManager.roles.protocolStatus;

import java.util.List;

public record returnProtocolsDTO(
        int protocolID,
        registerUserDTO user,
        String status,

        String port,
        String Returndate,
        String Returntime,

        String initDate,
        String initTime,
        List<portStatusDTO> ports


) {
}
