package com.uniquindio.reporte.utils;

import com.uniquindio.reporte.model.entities.User;
import com.uniquindio.reporte.model.enums.users.EnumUserType;
import com.uniquindio.reporte.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

@Slf4j
public class OperationUtils {

    @Autowired
    private UserService userService;

    public boolean isAdmin(String idUser) {

        try {
            ResponseDto responseDto = (ResponseDto) userService.getUser(idUser).getBody();

            if (responseDto != null && responseDto.getDatos() != null) {
                User user = (User) responseDto.getDatos();
                return (user.getUserType().equals(EnumUserType.ADMINISTRADOR));
            } else {
                return false;
            }
        } catch (Exception e) {
            log.error("Error obteniendo el usuario con id {}", idUser);
        }
        return false;
    }
}
