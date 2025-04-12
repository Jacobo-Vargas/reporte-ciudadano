package com.uniquindio.reporte.controllers;

import com.uniquindio.reporte.model.DTO.user.*;
import com.uniquindio.reporte.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    //crear usuario
    @PostMapping("/register")
    public ResponseEntity<?> createUser(@RequestBody @Valid CreateUserDTO createUserDTO) throws Exception {
        return userService.createUser(createUserDTO);
    }


    //actualizar usuario
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable String id, @RequestBody @Valid UpdateUserDto updateUserDto) throws Exception {
        return userService.updateUser(id,updateUserDto);
    }


    //  cambiar estado  de usuario
    @PutMapping("status")
    public ResponseEntity<?> changeUserStatus(@RequestBody @Valid ChangeUserStatusDto changeUserStatusDto) throws Exception {
        return userService.changeUserStatus(changeUserStatusDto);
    }


    //obtener usuario por id
    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable String id) throws Exception {
        return userService.getUser(id);
    }

    // obtener todos los usuarios
    @GetMapping()
    public ResponseEntity<?> getUsers() throws Exception {
        return userService.getUsers();
    }

    @GetMapping("/verifyEmailAndDocumentNumber")
    public  ResponseEntity<?> verifyEmailAndDocumentNumber(@RequestBody @Valid VerifyEmailAndDocumentNumberUserDto verifyEmailAndDocumentNumberUserDto)throws  Exception{
        return userService.verifyEmailAndDocumentNumber(verifyEmailAndDocumentNumberUserDto);
    }

    @PutMapping("/changeUserPassword")
    public  ResponseEntity<?>changeUserPassword(@RequestBody @Valid ChangeUserPassword changeUserPassword)throws  Exception{
        return  userService.changeUserPassword(changeUserPassword);

    }




}
