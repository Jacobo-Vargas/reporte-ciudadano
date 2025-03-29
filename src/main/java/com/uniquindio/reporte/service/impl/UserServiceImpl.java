package com.uniquindio.reporte.service.impl;

import com.uniquindio.reporte.mapper.UserMapper;
import com.uniquindio.reporte.model.DTO.CreateUserDTO;
import com.uniquindio.reporte.model.DTO.utilsDto.ResponseDto;
import com.uniquindio.reporte.model.entities.User;
import com.uniquindio.reporte.model.enums.users.EnumUserStatus;
import com.uniquindio.reporte.repository.UserRepository;
import com.uniquindio.reporte.service.UserService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRepository userRepository;

    @Override
    public ResponseEntity<?> createUser(CreateUserDTO createUserDTO) {

        if (userRepository.findByEmail(createUserDTO.email()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ResponseDto(409, "El email ya está registrado", "Email: " + createUserDTO.email()));
        }
        if (userRepository.findById(createUserDTO.documentNumber()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ResponseDto(409, "La cédula ya está registrada", "Teléfono: " + createUserDTO.phone()));
        }


        User user = userMapper.toDocument(createUserDTO);


        user.setScore(0);
        user.setCreatedAt(LocalDate.now());
        user.setUserStatus(EnumUserStatus.ACTIVE);


        return null;

    }

    @Override
    public ResponseEntity<?> updateUser() {
        return null;
    }

    @Override
    public ResponseEntity<?> changeUserStatus(ObjectId userId, String estado) {
        return null;
    }

    @Override
    public ResponseEntity<?> getUser(ObjectId userId) {
        return null;
    }

    @Override
    public ResponseEntity<?> getUsers() {
        return null;
    }
}

