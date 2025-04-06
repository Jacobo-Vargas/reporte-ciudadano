package com.uniquindio.reporte.service;

import com.uniquindio.reporte.model.DTO.user.ChangePasswordDto;
import com.uniquindio.reporte.model.DTO.user.CreateUserDTO;
import com.uniquindio.reporte.model.DTO.user.UpdateUserDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;

public interface UserService {

    ResponseEntity<?> createUser(CreateUserDTO createUserDTO) throws Exception;

    ResponseEntity<?> updateUser(String id,UpdateUserDto updateUserDto) throws Exception;

    ResponseEntity<?>changeUserStatus(String userId, String estado) throws Exception;

    ResponseEntity<?>getUser(String userId) throws Exception;

    ResponseEntity<?>getUsers() throws Exception;


    ResponseEntity<?> checkIfIdExists(String documentNumber) throws Exception;

    ResponseEntity<?> changePassword(String documentNumber, @Valid ChangePasswordDto changePasswordDto) throws Exception;
}
