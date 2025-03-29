package com.uniquindio.reporte.service;

import com.uniquindio.reporte.model.DTO.CreateUserDTO;
import com.uniquindio.reporte.model.entities.User;

public interface UserService {

    User createUser(CreateUserDTO userDTO)throws Exception;

}
