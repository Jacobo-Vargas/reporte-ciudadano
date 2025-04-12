package com.uniquindio.reporte.model.DTO.user.response;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record VerifyEmailAndDocumentNumberUserDto(
        @Email
        String email,
        @NotBlank
        String documentNumber
) {
}
