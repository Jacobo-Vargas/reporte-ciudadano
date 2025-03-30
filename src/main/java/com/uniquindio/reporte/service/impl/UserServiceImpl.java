package com.uniquindio.reporte.service.impl;

import com.uniquindio.reporte.mapper.UserMapper;
import com.uniquindio.reporte.model.DTO.CreateUserDTO;
import com.uniquindio.reporte.model.DTO.utilsDto.ResponseDto;
import com.uniquindio.reporte.model.entities.User;
import com.uniquindio.reporte.model.enums.users.EnumUserStatus;
import com.uniquindio.reporte.model.enums.users.EnumUserType;
import com.uniquindio.reporte.repository.UserRepository;
import com.uniquindio.reporte.service.UserService;
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
       Optional<User>existingUser = userRepository.findByEmailOrPhone(createUserDTO.email(),createUserDTO.phone());
        if (existingUser.isPresent()) {
            String message= existingUser.get().getEmail().equals(createUserDTO.email()) ?
                  String.format("El email %s ya está registrado", createUserDTO.email()) : String.format("La cedula ya está registrada",createUserDTO.documentNumber());
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ResponseDto(409, message, null));
        }


        //Mapeo del dto createUserDto al  documento user
        User user = userMapper.toDocument(createUserDTO);


        //Asignación de datos predeterminados
        user.setScore(0);
        user.setCreatedAt(LocalDate.now());
        user.setUserStatus(EnumUserStatus.ACTIVE);
        user.setUserType(EnumUserType.CLIENT);


        userRepository.save(user);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDto());
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

