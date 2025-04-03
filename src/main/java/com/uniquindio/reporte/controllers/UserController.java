package com.uniquindio.reporte.controllers;

import com.uniquindio.reporte.model.DTO.CreateUserDTO;
import com.uniquindio.reporte.model.DTO.UpdateUserDto;
import com.uniquindio.reporte.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
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
    @PostMapping()
    public ResponseEntity<?> createUser(@RequestBody @Valid CreateUserDTO createUserDTO) {
        return userService.createUser(createUserDTO);
    }

    //FALTA
    //actualizar usuario
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable String id, @RequestBody @Valid UpdateUserDto updateUserDto) {
        return userService.updateUser( new ObjectId(id),updateUserDto);
    }

    //FALTA
    //  cambiar estado  de usuario
    @PutMapping("/{documentNumber}/estado")
    public ResponseEntity<?> changeUserStatus(@PathVariable String documentNumber, @RequestParam String estado) {
        return userService.changeUserStatus(documentNumber, estado);
    }

    //FALTA
    //obtener usuario por id
    @GetMapping("/{documentNumber}")
    public ResponseEntity<?> getUser(@PathVariable String documentNumber) {
        return userService.getUser(documentNumber);
    }

    // obtener todos los usuarios
    @GetMapping()
    public ResponseEntity<?> getUsers() {
        return userService.getUsers();
    }


}
