package com.uniquindio.reporte.service;

import com.uniquindio.reporte.model.DTO.user.ChangeUserStatusDto;
import com.uniquindio.reporte.model.DTO.user.CreateUserDTO;
import com.uniquindio.reporte.model.DTO.user.UpdateUserDto;
import org.springframework.http.ResponseEntity;

public interface UserService {

    ResponseEntity<?> createUser(CreateUserDTO createUserDTO) throws Exception;

    ResponseEntity<?> updateUser(String id,UpdateUserDto updateUserDto) throws Exception;

    ResponseEntity<?>changeUserStatus(ChangeUserStatusDto changeUserStatusDto) throws Exception;

    ResponseEntity<?>getUser(String userId) throws Exception;

    ResponseEntity<?>getUsers() throws Exception;


}
