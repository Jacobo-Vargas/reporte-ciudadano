package com.uniquindio.reporte.model.DTO;

import java.util.List;

public record UserDTO(
        String id,
        String firstName,
        String middleName,
        String firstSurname,
        String secondLastName,
        String residenceCity,
        String phone,
        String address,
        String email,
        String password,

        //no se si estos van
        String userType,
        String userStatus,
        int punctuation,
        List<UserDTO> users
        ) {
}
