package com.uniquindio.reporte.model.DTO;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record CreateCategoryDTO(

        @NotBlank(message = "Debe registrar al menos un nombre")
        @Length(max = 50)
        String name,

        @NotBlank(message = "Debe registrar al menos un icono")
        @Length(max = 50)
        String icon,

        @NotBlank(message = "Debe registrar al menos un icono")
        @Length(max = 300)
        String description

){

}
