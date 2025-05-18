package com.uniquindio.reporte.model.DTO.comment;

import org.hibernate.validator.constraints.Length;

public record UpdateCommentDTO(

        @Length(min = 3, max = 150)
        String message

) {
}
