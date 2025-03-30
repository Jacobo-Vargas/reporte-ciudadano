package com.uniquindio.reporte.mapper;

import com.uniquindio.reporte.model.DTO.CreateUserDTO;
import com.uniquindio.reporte.model.DTO.UpdateUserDto;
import com.uniquindio.reporte.model.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "userType", constant = "CLIENTE")
    @Mapping(target = "enumUserStatus", constant = "ACTIVO")
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDate.now())")
    @Mapping(target = "score", constant = "0")
    User toDocumentCreate(CreateUserDTO userDTO);
    User toDocumentUpdate(UpdateUserDto updateUserDto);

   // UserDTO toDTO(User user);

//    void toDocument(EditUserDTO editUserDTO, @MappingTarget User user);
//
//    List<User> toDocumentList(List<CreateUserDTO> users);
//
//    List<UserDTO> toDTOList(List<User> users);
//
//    // Métodos para mapear ObjectId a String y viceversa
//    default String map(ObjectId value) {
//        return value != null ? value.toString() : null;
//    }
//
//    default ObjectId map(String value) {
//        return value != null ? new ObjectId(value) : null;
//    }
}
