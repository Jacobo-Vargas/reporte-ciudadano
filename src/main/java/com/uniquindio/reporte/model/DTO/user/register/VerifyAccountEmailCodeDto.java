package com.uniquindio.reporte.model.DTO.user.register;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record VerifyAccountEmailCodeDto(
        @Email
        String email,


        @NotBlank(message = "El codigo de confirmacion es obligatorio")
        String code
) {
}
