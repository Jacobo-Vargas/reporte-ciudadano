package com.uniquindio.reporte.service.impl;

import com.uniquindio.reporte.mapper.UserMapper;
import com.uniquindio.reporte.model.DTO.user.CreateUserDTO;
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
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDto(201, "Usuario creado exitosamente", "ID: " + user.getDocumentNumber()));
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
            // válida si el email ingresado es el mismo que tiene regsitrado a la cuenta, no lo almacene
            if(existingUser.get().getEmail().equals(updateUserDto.email())){
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(new ResponseDto(409,String.format("El email %s  ya se encuentra asociado a esta cuenta, deje el campo email vació",updateUserDto.email()),null));
            }
            //Entonces el email está asociado a otra cuenta
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
        CreateUserDTO userResponse = userMapper.toDTO(userRepository.save(user));

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
           CreateUserDTO userReponseDto= userMapper.toDTO( userRepository.save(user));
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

        CreateUserDTO userResponse = userMapper.toDTO(user);
        return ResponseEntity.ok(new ResponseDto(200, "El usuario ha sido encontrado exitosamente", userResponse));

    }

    @Override
    public ResponseEntity<?> getUsers() throws Exception {

        List<User> userList = userRepository.findAll();

        String message = userList.isEmpty() ? "No hay usuarios registrados en el sistema" : "Usuarios encontrados";

        List<CreateUserDTO> listDto = userMapper.toDTOList(userList);
        return ResponseEntity.ok(new ResponseDto(200, message, listDto));
    }
}

