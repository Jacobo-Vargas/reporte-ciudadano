package com.uniquindio.reporte.controllers;

import com.uniquindio.reporte.model.DTO.user.CreateUserDTO;
import com.uniquindio.reporte.model.DTO.user.UpdateUserDto;
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
    @PostMapping()
    public ResponseEntity<?> createUser(@RequestBody @Valid CreateUserDTO createUserDTO) throws Exception {
        return userService.createUser(createUserDTO);
    }


    //actualizar usuario
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable String id, @RequestBody @Valid UpdateUserDto updateUserDto) throws Exception {
        return userService.updateUser(id,updateUserDto);
    }

    //FALTA
    //  cambiar estado  de usuario
    @PutMapping("/{id}/estado")
    public ResponseEntity<?> changeUserStatus(@PathVariable String id, @RequestParam String estado) throws Exception {
        return userService.changeUserStatus(id, estado);
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

    @GetMapping("/users/exists/{id}")
    public ResponseEntity<?>checkIfIdExists(@PathVariable String id) throws Exception {
        return userService.checkIfIdExists(id);
    }


}
