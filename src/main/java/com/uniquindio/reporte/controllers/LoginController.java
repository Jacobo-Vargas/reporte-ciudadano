package com.uniquindio.reporte.controllers;

import com.uniquindio.reporte.model.DTO.loggin.LoginRequestDto;
import com.uniquindio.reporte.model.DTO.loggin.TokenDTO;
import com.uniquindio.reporte.service.impl.LoginServiceImpl;
import com.uniquindio.reporte.utils.ResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class LoginController {

    @Autowired
    private LoginServiceImpl loginServiceImpl;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto loginRequestDto) {
        try {
            TokenDTO token = loginServiceImpl.login(loginRequestDto);
            return ResponseEntity.ok(token);
        } catch (Exception e) {

            ResponseDto responseDto = new ResponseDto(HttpStatus.UNAUTHORIZED.value(), e.getMessage(), null);
            return ResponseEntity
                    .status(responseDto.getCodigo())
                    .body(responseDto);
        }
    }
}
