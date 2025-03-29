package com.uniquindio.reporte.model.DTO;

import com.uniquindio.reporte.model.enums.users.EnumResidenceCity;
import jakarta.validation.constraints.*;
import org.bson.types.ObjectId;
import org.hibernate.validator.constraints.Length;

public record CreateUserDTO(

        @NotBlank(message = "El campo de la cédula no puede estar vacío")
        @Length(max = 20)
        ObjectId documentNumber,

        @NotBlank(message = "Debe registrar al menos un nombre")
        @Length(max = 50)
        String name,


        @NotBlank(message = "Debe Registrar una ciudad de residencia")
        @Length(min = 3,max = 50)
        String residenceCity,

        @NotBlank(message = "El número de celular es obligatorio")
        @Pattern(regexp = "^\\d{10}$", message = "El número de celular debe tener 10 dígitos")
        String phone,

        @NotBlank(message = "La dirección es obligatoria")
        @Length(min = 10, max = 100)
        String address,

        @NotBlank(message = "El email es obligatorio")
        @Email(message = "El email debe tener un formato válido")
        String email,

        @NotBlank(message = "La contraseña es obligatoria")
        @Size(min = 8, max = 20, message = "La contraseña debe tener entre 8 y 20 caracteres")
        @Pattern(
                regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]+$",
                message = "La contraseña debe contener al menos una mayúscula, una minúscula, un número y un carácter especial"
        )
        String password
){
}
