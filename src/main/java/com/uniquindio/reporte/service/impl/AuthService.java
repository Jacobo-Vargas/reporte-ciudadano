package com.uniquindio.reporte.service.impl;



import com.uniquindio.reporte.model.DTO.loggin.LoginRequestDto;
import com.uniquindio.reporte.model.entities.User;
import com.uniquindio.reporte.repository.UserRepository;
import com.uniquindio.reporte.utils.JWTUtil;
import com.uniquindio.reporte.utils.JWTUtil;
import com.uniquindio.reporte.utils.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTUtil jwtUtil;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JWTUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public ResponseEntity<ResponseDto> login(LoginRequestDto loginRequest) {
        Optional<User> optionalUser = userRepository.findByEmail(loginRequest.email());

        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ResponseDto(401, "Credenciales incorrectas", null));
        }

        User user = optionalUser.get();

        if (!passwordEncoder.matches(loginRequest.password(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ResponseDto(401, "Credenciales incorrectas", null));
        }

        String token = jwtUtil.generateToken(user.getEmail());

        return ResponseEntity.ok(new ResponseDto(200, "Login exitoso", token));
    }
}
