package com.uniquindio.reporte.model.DTO.user.register;

import com.uniquindio.reporte.model.enums.users.EnumUserStatus;
import jakarta.validation.constraints.NotNull;

public record ChangeUserStatusDto(

        @NotNull(message = "El ID del usuario no puede ser nulo")
        String userId,

        @NotNull(message = "El nuevo estado del usuario es obligatorio")
        EnumUserStatus newStatus
        ) {
}
