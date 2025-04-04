package com.uniquindio.reporte.model.DTO.comment;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record CreateCommentDTO(

        @NotBlank(message = "Debe ingresar un mensaje")
        @Length(max = 400)
        String message,

        @NotBlank(message = "Debe ingresar el id del cliente")
        @Length(max = 400)
        String userId,

        @NotBlank(message = "Debe ingresar el id del reporte")
        @Length(max = 400)
        String reportId




) {

}
