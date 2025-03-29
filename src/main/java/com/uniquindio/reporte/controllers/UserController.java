package com.uniquindio.reporte.controllers;

import com.uniquindio.reporte.model.DTO.CreateUserDTO;
import com.uniquindio.reporte.model.DTO.MessageDTO;
import com.uniquindio.reporte.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<MessageDTO<String>> create(@RequestBody CreateUserDTO userDTO) throws Exception {
        userService.createUser(userDTO);
        return ResponseEntity.status(201).body(new MessageDTO<>(false, "User Created"));
    }
}
