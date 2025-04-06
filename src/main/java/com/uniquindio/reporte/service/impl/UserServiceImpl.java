package com.uniquindio.reporte.service.impl;

import com.uniquindio.reporte.mapper.UserMapper;
import com.uniquindio.reporte.model.DTO.user.ChangePasswordDto;
import com.uniquindio.reporte.model.DTO.user.CreateUserDTO;
import com.uniquindio.reporte.model.DTO.user.ResponseUserDto;
import com.uniquindio.reporte.model.DTO.user.UpdateUserDto;
import com.uniquindio.reporte.model.entities.User;
import com.uniquindio.reporte.model.enums.users.EnumResidenceCity;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Override
    public ResponseEntity<?> createUser(CreateUserDTO createUserDTO) throws Exception {
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
        user.setDocumentNumber(createUserDTO.documentNumber());

        user.setPassword(passwordEncoder.encode(createUserDTO.password()));
        userRepository.save(user);

        ResponseUserDto responseUserDto= userMapper.toDTOReponse(user);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDto(201, "Usuario creado exitosamente", responseUserDto));
    }

    @Override
    public ResponseEntity<?> updateUser(String id, UpdateUserDto updateUserDto) throws Exception {
        ObjectId objectId = new ObjectId(id);
        Optional<User> optionalUser = userRepository.findById(objectId);

        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseDto(404, "Usuario no encontrado", null));
        }
        //Verificación sobre los datos email y phone, para asegurarnos que no se repitan
        Optional<User> existingUser = userRepository.findByEmail(updateUserDto.email());
        if (existingUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ResponseDto(409, String.format("El email %s ya está registrado", updateUserDto.email()), null));
        }
        User updatedData = userMapper.toDocumentUpdate(updateUserDto);
        User user = optionalUser.get();
        // Actualizar solo los campos no nulos del DTO
        if (updateUserDto.email() != null) {
            user.setEmail(updateUserDto.email());
        }
        if (updateUserDto.phone() != null) {
            user.setPhone(updateUserDto.phone());
        }
        if (updateUserDto.residenceCity() != null) {
            try {
                user.setResidenceCity(EnumResidenceCity.valueOf(updateUserDto.residenceCity().toUpperCase()));
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException(
                        String.format("La ciudad de residencia '%s' no es válida. Opciones disponibles: %s",
                                updateUserDto.residenceCity(),
                                Arrays.toString(EnumResidenceCity.values()))
                );
            }
        }
        if (updateUserDto.address() != null) {
            user.setName(updateUserDto.address());
        }
        // Guardar cambios en la base de datos
        ResponseUserDto userResponse = userMapper.toDTOReponse(userRepository.save(user));

        return ResponseEntity.ok(new ResponseDto(200, "Usuario actualizado exitosamente", userResponse));
    }

    @Override
    public ResponseEntity<?> changeUserStatus(String id, String estado) throws Exception {
        ObjectId objectId = new ObjectId(id);
        Optional<User> optionalUser = userRepository.findById(objectId);
        User user = userRepository.findById(objectId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));
        try {
            EnumUserStatus nuevoEstado = EnumUserStatus.valueOf(estado.toUpperCase());
            user.setEnumUserStatus(nuevoEstado);
           ResponseUserDto userReponseDto= userMapper.toDTOReponse( userRepository.save(user));
            return ResponseEntity.ok(new ResponseDto(200, "Estado actualizado exitosamente", userReponseDto));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseDto(400, "Estado inválido: " + estado, null));
        }

    }

    @Override
    public ResponseEntity<?> getUser(String id) throws Exception {
        ObjectId objectId = new ObjectId(id);
        User user = userRepository.findById(objectId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

        ResponseUserDto userResponse = userMapper.toDTOReponse(user);
        return ResponseEntity.ok(new ResponseDto(200, "El usuario ha sido encontrado exitosamente", userResponse));

    }

    @Override
    public ResponseEntity<?> getUsers() throws Exception {

        List<User> userList = userRepository.findAll();

        String message = userList.isEmpty() ? "No hay usuarios registrados en el sistema" : "Usuarios encontrados";

        List<ResponseUserDto> listDto = userMapper.toDTOListResponse(userList);
        return ResponseEntity.ok(new ResponseDto(200, message, listDto));
    }

    @Override
    public ResponseEntity<?> checkIfIdExists(String documentNumber) throws Exception {
        if(userRepository.existsByDocumentNumber(documentNumber)){
            return ResponseEntity.ok(new ResponseDto(200, "Hemos encontrado al usuario que tiene asociado ese numero de identificación", true));
        }
        else {
            return ResponseEntity.ok(new ResponseDto(200, "No hay ningún usuario relacionado con esa identificación", false));
        }
    }

    @Override
    public ResponseEntity<?> changePassword(String documentNumber, ChangePasswordDto changePasswordDto) throws Exception {
        if(userRepository.existsByDocumentNumber(documentNumber)){
            User user= userRepository.findByDocumentNumber(documentNumber);
            user.setPassword(changePasswordDto.newPassword());
            user.setPassword(passwordEncoder.encode(changePasswordDto.newPassword()));
            userRepository.save(user);
            return ResponseEntity.ok(new ResponseDto(200, "Se ha cambiado la clave con existo", true));
        }else {
            return ResponseEntity.ok(new ResponseDto(200, "No ha sido posible cambiar la clave", true));
        }
    }
}

