package com.uniquindio.reporte.model.DTO.notification;

import jakarta.validation.constraints.NotNull;

public record CreateNotificationDTO(

        @NotNull(message = "El mensaje es obligatorio")
        String message,

        @NotNull(message = "El tipo es obligatorio")
        String type,

        @NotNull(message = "El ID del reporte es obligatorio")
        String reportId,

        @NotNull(message = "El ID del usuario es obligatorio")
        String userId
){
}