package com.uniquindio.reporte.mapper;

import com.uniquindio.reporte.model.DTO.user.register.CreateUserDTO;
import com.uniquindio.reporte.model.DTO.user.response.ResponseUserDto;
import com.uniquindio.reporte.model.DTO.user.response.ResponseUserStatusDto;
import com.uniquindio.reporte.model.DTO.user.register.UpdateUserDto;
import com.uniquindio.reporte.model.entities.User;
import com.uniquindio.reporte.utils.ObjectIdMapperUtil;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ObjectIdMapperUtil.class})
public interface UserMapper {

    @Mapping(target = "userType", constant = "CLIENTE")
    @Mapping(target = "enumUserStatus", constant = "ACTIVO")
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDate.now())")
    @Mapping(target = "score", constant = "0")
    User toDocumentCreate(CreateUserDTO userDTO);

    User toDocumentUpdate(UpdateUserDto updateUserDto);

    CreateUserDTO toDTO(User user);

    ResponseUserDto toDtoResponseUser(User user);

    List<ResponseUserDto>toDTOListReponseUser(List<User> users);

    ResponseUserStatusDto toDocumentResponseUserStatus(User user);

}
