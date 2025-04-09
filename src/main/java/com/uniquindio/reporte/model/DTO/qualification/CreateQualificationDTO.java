package com.uniquindio.reporte.model.DTO.qualification;

import jakarta.validation.constraints.NotNull;

public record CreateQualificationDTO(

        @NotNull(message = "La reacción es obligatoria")
        Integer reaction,

        @NotNull(message = "El ID del reporte es obligatorio")
        String reportId,

        @NotNull(message = "El ID del usuario es obligatorio")
        String userId

) {}
