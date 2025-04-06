package com.uniquindio.reporte.model.DTO.user;

import java.time.LocalDateTime;

public record ResponseUserDto(
        String id,
        String documentNumber,
        String name,
        String email,
        String phone,
        String address,
        String residenceCity,
        String userType,
        String userStatus,
        int score,
        LocalDateTime dateOfBirth,
        LocalDateTime createdAt
) {
}
