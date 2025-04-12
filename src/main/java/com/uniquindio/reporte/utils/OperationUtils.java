package com.uniquindio.reporte.utils;

import com.uniquindio.reporte.model.entities.User;
import com.uniquindio.reporte.model.enums.users.EnumUserType;
import com.uniquindio.reporte.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.stream.Collectors;

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

    public static boolean validateUserByRol(String rol) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        List<String> roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        return roles.stream().anyMatch(item -> item.equals("ROLE_".concat(rol)));
    }
}
