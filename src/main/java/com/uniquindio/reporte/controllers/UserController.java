package com.uniquindio.reporte.controllers;

import com.uniquindio.reporte.model.DTO.CreateUserDTO;
import com.uniquindio.reporte.model.DTO.MessageDTO;
import com.uniquindio.reporte.service.UserService;
import com.uniquindio.reporte.service.impl.UserServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.mapstruct.control.MappingControl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserServiceImpl userServiceImpl;

    //crear usuario
    @PostMapping()
    public ResponseEntity<?> createUser(@RequestBody @Valid CreateUserDTO createUserDTO) {
        return userServiceImpl.createUser(createUserDTO);
    }

    //actualizar usuario
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable ObjectId id) {
        return ResponseEntity.ok().body("Sus datos han sido modificado con exito");
    }

    //  cambiar estado  de usuario
    @PostMapping("/{id}/estado")
    public ResponseEntity<?> changeUserStatus(@PathVariable ObjectId id, @RequestParam String estado) {
        return ResponseEntity.ok().body("La cuenta  ha cambiado de estado");
    }

    //obtener usuario por id
    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable ObjectId id) {
        return ResponseEntity.ok().body("");
    }

    // obtener todos los usuarios
    @GetMapping("")
    public ResponseEntity<?> getUsers() {
        return ResponseEntity.ok().body(" ");
    }


}
