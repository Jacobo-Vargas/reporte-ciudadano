package com.uniquindio.reporte.model.DTO.loggin;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record TokenDTO(String token,
                       String id,
                       String documentNumber,
                       String name,
                       String email,
                       String phone,
                       String address,
                       String residenceCity,
                       String userType,
                       String userStatus,
                       int score
){}
