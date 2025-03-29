package com.uniquindio.reporte.model.DTO;

import com.uniquindio.reporte.model.enums.users.EnumResidenceCity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public record CreateUserDTO(
        @NotBlank
        @Length(max = 100) String name,
        @Length(max = 10) String phone,
        @NotNull EnumResidenceCity residenceCity,
        @NotBlank @Length(max = 100) String address,
        @NotBlank @Length(max = 50) @Email String email,
        @NotBlank @Length(min = 7, max = 20) String password
){
}
