package com.uniquindio.reporte.service;

import com.uniquindio.reporte.model.DTO.user.*;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;

public interface UserService {

    ResponseEntity<?> createUser(CreateUserDTO createUserDTO) throws Exception;

    ResponseEntity<?> updateUser(String id,UpdateUserDto updateUserDto) throws Exception;

    ResponseEntity<?>changeUserStatus(ChangeUserStatusDto changeUserStatusDto) throws Exception;

    ResponseEntity<?>getUser(String userId) throws Exception;

    ResponseEntity<?>getUsers() throws Exception;

    ResponseEntity<?> verifyEmailAndDocumentNumber(VerifyEmailAndDocumentNumberUserDto verifyEmailAndDocumentNumberUserDto);

    ResponseEntity<?> changeUserPassword(ChangeUserPassword changeUserPassword) throws  Exception;
}
