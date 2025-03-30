package com.uniquindio.reporte.service.impl;

import com.uniquindio.reporte.mapper.UserMapper;
import com.uniquindio.reporte.model.DTO.CreateUserDTO;
import com.uniquindio.reporte.model.DTO.UpdateUserDto;
import com.uniquindio.reporte.model.entities.User;
import com.uniquindio.reporte.model.enums.users.EnumUserStatus;
import com.uniquindio.reporte.model.enums.users.EnumUserType;
import com.uniquindio.reporte.repository.UserRepository;
import com.uniquindio.reporte.service.UserService;
import com.uniquindio.reporte.utils.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRepository userRepository;

    @Override
    public ResponseEntity<?> createUser(CreateUserDTO createUserDTO) {
        //Verificación sobre los datos email y phone, para asegurarnos que no se repitan
        Optional<User> existingUser = userRepository.findByEmailOrDocumentNumber(createUserDTO.email(), createUserDTO.documentNumber());
        if (existingUser.isPresent()) {
            String message = existingUser.get().getEmail().equals(createUserDTO.email()) ?
                    String.format("El email %s ya está registrado", createUserDTO.email()) : String.format("La cedula %s ya está registrada", createUserDTO.documentNumber());
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ResponseDto(409, message, null));
        }
        //Mapeo del dto createUserDto al  documento user
        User user = userMapper.toDocumentCreate(createUserDTO);
        //Asignación de datos predeterminados
        user.setScore(0);
        user.setCreatedAt(LocalDate.now());
        user.setEnumUserStatus(EnumUserStatus.ACTIVO);
        user.setUserType(EnumUserType.CLIENTE);
        userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDto(201, "Usuario creado exitosamente", "ID: " + user.getDocumentNumber()));
    }

    @Override
    public ResponseEntity<?> updateUser(UpdateUserDto updateUserDto) {
        Optional<User> user = userRepository.findByDocumentNumber(updateUserDto.documentNumber())
        
        //Verificación sobre los datos email y phone, para asegurarnos que no se repitan
        Optional<User> existingUser = userRepository.findByEmail(updateUserDto.email());
        if (existingUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ResponseDto(409, String.format("El email %s ya está registrado", updateUserDto.email()), null));
        }

        User updatedData = userMapper.toDocumentUpdate(updateUserDto);

        if (updatedData.getResidenceCity() != null) {


        }


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

