package com.uniquindio.reporte.model.DTO.user.response;

import com.uniquindio.reporte.model.entities.CodeValidation;
import com.uniquindio.reporte.model.entities.User;
import com.uniquindio.reporte.model.enums.users.EnumResidenceCity;
import com.uniquindio.reporte.model.enums.users.EnumUserStatus;
import com.uniquindio.reporte.model.enums.users.EnumUserType;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public record ResponseUserDto(

        String id,
        String documentNumber,
        String name,
        EnumResidenceCity residenceCity,
        String email,
        String phone,
        String address,
        String password,
        EnumUserType userType,
        EnumUserStatus enumUserStatus,
        List<Map<String, Integer>> followers,
        int score,
        LocalDate createdAt,
        CodeValidation codeValidation
) {
}
