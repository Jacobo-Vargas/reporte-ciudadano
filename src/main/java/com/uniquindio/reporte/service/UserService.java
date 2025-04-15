package com.uniquindio.reporte.service;

import com.uniquindio.reporte.model.DTO.user.register.*;
import com.uniquindio.reporte.model.DTO.user.response.VerifyEmailAndDocumentNumberUserDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

public interface UserService {

    ResponseEntity<?> sendCodeConfirmation(String email) throws Exception;

    ResponseEntity<?> updateUser(String id, UpdateUserDto updateUserDto) throws Exception;

    ResponseEntity<?>changeUserStatus(ChangeUserStatusDto changeUserStatusDto) throws Exception;

    ResponseEntity<?>getUser(String userId) throws Exception;

    ResponseEntity<?>getUsers() throws Exception;

    ResponseEntity<?> verifyEmailAndDocumentNumber(VerifyEmailAndDocumentNumberUserDto verifyEmailAndDocumentNumberUserDto);

    ResponseEntity<?> changeUserPassword(ChangeUserPassword changeUserPassword) throws  Exception;

    ResponseEntity<?> createUser(CreateUserDTO createUserDTO)throws Exception;

    ResponseEntity<?> verifyAccountEmailCode(VerifyAccountEmailCodeDto verifyAccountEmailCodeDto) throws  Exception;

    ResponseEntity<?> addFollowerToUser(FollowerRequestDto dto)throws Exception;

    ResponseEntity<?> removeFollowerFromUser(FollowerRequestDto dto) throws Exception;
}
