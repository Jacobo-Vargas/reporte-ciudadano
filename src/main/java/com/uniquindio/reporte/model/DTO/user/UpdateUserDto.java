package com.uniquindio.reporte.model.DTO.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

public record UpdateUserDto(
        @NotBlank(message = "Debe Registrar una ciudad de residencia")
        @Length(min = 3, max = 50)
        String residenceCity,

        @Pattern(regexp = "^\\d{10}$", message = "El número de celular debe tener 10 dígitos")
        String phone,

        @Length(max = 100)
        String address,

        @Email(message = "El email debe tener un formato válido")
        String email

) {
}
