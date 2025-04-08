package com.uniquindio.reporte.model.DTO.user;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record ChangePasswordDto(
        @NotBlank(message = "La contraseña es obligatoria")
        @Length(min = 6)
        String newPassword
) {
}
