package com.uniquindio.reporte.model.DTO.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.uniquindio.reporte.model.entities.CodeValidation;
import com.uniquindio.reporte.model.entities.User;
import com.uniquindio.reporte.model.enums.users.EnumResidenceCity;
import com.uniquindio.reporte.model.enums.users.EnumUserStatus;
import com.uniquindio.reporte.model.enums.users.EnumUserType;
import org.bson.types.ObjectId;

import java.time.LocalDate;
import java.util.List;

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
        List<User> followers,
        int score,
        LocalDate createdAt,
        CodeValidation codeValidation
) {
}
