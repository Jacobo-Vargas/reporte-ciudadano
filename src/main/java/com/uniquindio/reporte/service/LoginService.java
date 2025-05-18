package com.uniquindio.reporte.service;

import com.uniquindio.reporte.model.DTO.loggin.LoginRequestDto;
import com.uniquindio.reporte.model.DTO.loggin.TokenDTO;
import com.uniquindio.reporte.model.entities.User;

import java.util.Map;

public interface LoginService {
    public TokenDTO login(LoginRequestDto loginDTO) throws Exception;
}
