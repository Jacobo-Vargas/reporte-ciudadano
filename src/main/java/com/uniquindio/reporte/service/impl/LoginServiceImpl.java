package com.uniquindio.reporte.service.impl;

import com.uniquindio.reporte.model.DTO.loggin.LoginRequestDto;
import com.uniquindio.reporte.model.DTO.loggin.TokenDTO;
import com.uniquindio.reporte.model.entities.User;
import com.uniquindio.reporte.model.enums.users.EnumUserStatus;
import com.uniquindio.reporte.repository.UserRepository;
import com.uniquindio.reporte.security.JWTUtils;
import com.uniquindio.reporte.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public TokenDTO login(LoginRequestDto loginDTO) throws Exception {
        Optional<User> optionalUsuario = userRepository.findByEmail(loginDTO.email());

        if (optionalUsuario.isEmpty()) {
            throw new Exception("El usuario no existe");
        }

        User user = optionalUsuario.get();

        // Verificar si la contraseña es correcta
        if (!passwordEncoder.matches(loginDTO.password(), user.getPassword())) {
            throw new Exception("Credenciales incorrectas.");
        }

        if (!EnumUserStatus.ACTIVO.name().equals(user.getEnumUserStatus().name())) {
            throw new Exception("La cuenta no esta activa, estado: ".concat(user.getEnumUserStatus().name()));
        }

        String token = jwtUtils.generateToken(user.getId().toString(), crearClaims(user));

        return new TokenDTO(token);
    }

    private Map<String, String> crearClaims(User usuario) {
        return Map.of(
                "email", usuario.getEmail(),
                "nombre", usuario.getName(),
                "rol", "ROLE_" + usuario.getUserType().name()
        );
    }

}
