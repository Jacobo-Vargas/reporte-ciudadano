package com.uniquindio.reporte.mapper;

import com.uniquindio.reporte.model.entities.DTO.CrearUsuarioDTO;
import com.uniquindio.reporte.model.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(source = "firstName", target = "firstName")
    CrearUsuarioDTO toDto(User user);
}
