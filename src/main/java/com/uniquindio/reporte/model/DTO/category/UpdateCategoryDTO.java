package com.uniquindio.reporte.model.DTO.category;

import org.hibernate.validator.constraints.Length;

public record UpdateCategoryDTO(

        @Length(min = 3, max = 50)
        String name,

        @Length(min = 3, max = 150)
        String icon,

        @Length(min = 3, max = 300)
        String description
) {
}
