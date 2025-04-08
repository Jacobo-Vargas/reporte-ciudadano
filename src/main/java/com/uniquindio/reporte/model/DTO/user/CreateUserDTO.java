package com.uniquindio.reporte.model.DTO.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;

public record CreateUserDTO(
        @NotBlank(message = "Debe registrar al menos un nombre")
        @Length(max = 50)
        String name,

        @NotBlank
        String documentNumber,

        @NotNull
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy")
        LocalDate dateOfBirth,

        @NotBlank(message = "Debe registrar un pais")
        @NotNull
        String country,

        @NotBlank(message = "Debe Registrar una ciudad de residencia")
        @Length(min = 3, max = 50)
        String residenceCity,

        @NotBlank(message = "La dirección es obligatoria")
        @Length(max = 100)
        String address,

        @NotBlank(message = "El número de celular es obligatorio")
        @Pattern(regexp = "^\\d{10}$", message = "El número de celular debe tener 10 dígitos")
        String phone,

        @NotBlank(message = "El email es obligatorio")
        @Email(message = "El email debe tener un formato válido")
        String email,

        @NotBlank(message = "La contraseña es obligatoria")
        @Length(min = 6)
        String password,

        @JsonProperty("id")
        @JsonSerialize(using = ToStringSerializer.class)
        String id //eliminar no debe ir


) {
}
