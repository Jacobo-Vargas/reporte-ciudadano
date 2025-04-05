package com.uniquindio.reporte.controllers;

import com.uniquindio.reporte.model.DTO.loggin.LoginRequestDto;
import com.uniquindio.reporte.model.DTO.loggin.TokenDTO;
import com.uniquindio.reporte.service.impl.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto loginRequestDto) {
        try {
            TokenDTO token = loginService.login(loginRequestDto);
            return ResponseEntity.ok(token);
        } catch (Exception e) {
            return ResponseEntity
                    .status(401)
                    .body("Error al iniciar sesión: " + e.getMessage());
        }
    }
}
