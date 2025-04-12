package com.uniquindio.reporte.service;

import com.uniquindio.reporte.model.DTO.user.register.ChangeUserPassword;
import com.uniquindio.reporte.model.DTO.user.register.ChangeUserStatusDto;
import com.uniquindio.reporte.model.DTO.user.register.CreateUserDTO;
import com.uniquindio.reporte.model.DTO.user.register.UpdateUserDto;
import com.uniquindio.reporte.model.DTO.user.response.VerifyEmailAndDocumentNumberUserDto;
import org.springframework.http.ResponseEntity;

public interface UserService {

    ResponseEntity<?> sendCodeConfirmation(CreateUserDTO createUserDTO) throws Exception;

    ResponseEntity<?> updateUser(String id, UpdateUserDto updateUserDto) throws Exception;

    ResponseEntity<?>changeUserStatus(ChangeUserStatusDto changeUserStatusDto) throws Exception;

    ResponseEntity<?>getUser(String userId) throws Exception;

    ResponseEntity<?>getUsers() throws Exception;

    ResponseEntity<?> verifyEmailAndDocumentNumber(VerifyEmailAndDocumentNumberUserDto verifyEmailAndDocumentNumberUserDto);

    ResponseEntity<?> changeUserPassword(ChangeUserPassword changeUserPassword) throws  Exception;

    ResponseEntity<?> createUser(CreateUserDTO createUserDTO, String code)throws Exception;
}
