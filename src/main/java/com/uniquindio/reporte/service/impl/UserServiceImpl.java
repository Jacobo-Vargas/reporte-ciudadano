package com.uniquindio.reporte.service.impl;

import com.uniquindio.reporte.mapper.UserMapper;
import com.uniquindio.reporte.model.DTO.user.register.ChangeUserPassword;
import com.uniquindio.reporte.model.DTO.user.register.ChangeUserStatusDto;
import com.uniquindio.reporte.model.DTO.user.register.CreateUserDTO;
import com.uniquindio.reporte.model.DTO.user.register.UpdateUserDto;
import com.uniquindio.reporte.model.DTO.user.response.ResponseUserDto;
import com.uniquindio.reporte.model.DTO.user.response.ResponseUserStatusDto;
import com.uniquindio.reporte.model.DTO.user.response.VerifyEmailAndDocumentNumberUserDto;
import com.uniquindio.reporte.model.entities.CodeValidation;
import com.uniquindio.reporte.model.entities.User;
import com.uniquindio.reporte.model.enums.users.EnumResidenceCity;
import com.uniquindio.reporte.model.enums.users.EnumUserStatus;
import com.uniquindio.reporte.model.enums.users.EnumUserType;
import com.uniquindio.reporte.repository.UserRepository;
import com.uniquindio.reporte.service.EmailService;
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
import java.time.LocalDateTime;
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
    @Autowired
    private VerificationCodeService verificationCodeService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private CodeValidationServiceIpml codeValidationServiceIpml;

    @Override
    public ResponseEntity<?> sendCodeConfirmation(CreateUserDTO createUserDTO) throws Exception {
        try {
            //Verificación sobre los datos email y phone, para asegurarnos que no se repitan
            Optional<User> existingUser = userRepository.findByEmailOrDocumentNumber(createUserDTO.email(), createUserDTO.documentNumber());
            if (existingUser.isPresent()) {
                String message = existingUser.get().getEmail().equals(createUserDTO.email()) ?
                        String.format("El email %s ya está registrado", createUserDTO.email()) : String.format("La cedula %s ya está registrada", createUserDTO.documentNumber());
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(new ResponseDto(409, message, null));
            }
            // Generar código y enviarlo
            String code = verificationCodeService.generateAndStoreCode(createUserDTO.email());

            emailService.sendCodeVerifaction(createUserDTO.email(), code);
            //guardar en la base de datos el cod
            codeValidationServiceIpml.saveCode(code, createUserDTO.email());

            return ResponseEntity.status(HttpStatus.ACCEPTED)
                    .body(new ResponseDto(200, "Código de verificación enviado al correo electrónico: ", createUserDTO.email()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public ResponseEntity<?> createUser(CreateUserDTO createUserDTO, String code) throws Exception {
        Optional<CodeValidation> codeValidation = codeValidationServiceIpml.getCodeValidation(code);
        if (codeValidation.isPresent()) {
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime expiresAt = codeValidation.get().getExpiresAt();
            if (now.isBefore(expiresAt)) {
                if (codeValidation.get().getCode().equals(code)) {
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
                    ResponseUserDto responseUserDto = userMapper.toDtoResponseUser(userRepository.save(user));
                    return ResponseEntity.status(HttpStatus.CREATED)
                            .body(new ResponseDto(200, "Usuario creado exitosamente", responseUserDto));
                }
            }
            else {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(new ResponseDto(410, "El código ha expirado", code));

            }
        }
        else {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ResponseDto(409, String.format("El codigo  %s  no es correcto ", code), null));
        }
        return null;
    }




    @Override
    public ResponseEntity<?> updateUser(String id, UpdateUserDto updateUserDto) throws Exception {
        ObjectId objectId = new ObjectId(id);
        Optional<User> optionalUser = userRepository.findById(objectId);

        if (optionalUser.isEmpty()) {
            if (optionalUser.get().getEmail().equals(updateUserDto.email())) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(new ResponseDto(409, String.format("El email %s  ya se encuntra asociado a esta cuenta", updateUserDto.email()), null));
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseDto(404, "Usuario no encontrado", null));
        }
        //Verificación sobre los datos email y phone, para asegurarnos que no se repitan
        Optional<User> existingUser = userRepository.findByEmail(updateUserDto.email());


        if (existingUser.isPresent()) {
            // válida si el email ingresado es el mismo que tiene regsitrado a la cuenta, no lo almacene
            if (existingUser.get().getEmail().equals(updateUserDto.email())) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(new ResponseDto(409, String.format("El email %s  ya se encuentra asociado a esta cuenta, deje el campo email vació", updateUserDto.email()), null));
            }
            //Entonces el email está asociado a otra cuenta
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ResponseDto(409, String.format("El email %s ya está registrado", updateUserDto.email()), null));
        }
        User updatedData = userMapper.toDocumentUpdate(updateUserDto);
        User user = optionalUser.get();
        // Actualizar solo los campos no nulos del DTO
        if (updatedData.getEmail() != null && !updateUserDto.email().isEmpty()) {
            user.setEmail(updateUserDto.email());
        }
        if (updateUserDto.phone() != null || !updateUserDto.phone().isEmpty()) {
            user.setPhone(updateUserDto.phone());
        }
        if (updateUserDto.residenceCity() != null && !updatedData.getResidenceCity().describeConstable().isEmpty()) {
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
        if (updateUserDto.address() != null && !updatedData.getAddress().isEmpty()) {
            user.setAddress(updateUserDto.address());
        }
        // Guardar cambios en la base de datos
        ResponseUserDto responseUserDto = userMapper.toDtoResponseUser(userRepository.save(user));
        return ResponseEntity.ok(new ResponseDto(200, "Usuario actualizado exitosamente", responseUserDto));
    }

    @Override
    public ResponseEntity<?> changeUserStatus(ChangeUserStatusDto changeUserStatusDto) throws Exception {
        ObjectId objectId = new ObjectId(changeUserStatusDto.userId());
        Optional<User> optionalUser = userRepository.findById(objectId);
        User user = userRepository.findById(objectId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));
        try {
            EnumUserStatus nuevoEstado = changeUserStatusDto.newStatus();
            if (user.getEnumUserStatus().equals(nuevoEstado)) {
                ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(new ResponseDto(409, String.format("La cuenta ya se encuentra en este estado %s", nuevoEstado), null));
            }
            user.setEnumUserStatus(nuevoEstado);
            ResponseUserStatusDto responseUserStatusDto = userMapper.toDocumentResponseUserStatus(userRepository.save(user));
            return ResponseEntity.ok(new ResponseDto(200, "Estado actualizado exitosamente", responseUserStatusDto));// cambiar respuesta
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseDto(400, "Estado inválido: " + changeUserStatusDto.newStatus(), null));
        }
    }

    @Override
    public ResponseEntity<?> getUser(String id) throws Exception {
        ObjectId objectId = new ObjectId(id);
        User user = userRepository.findById(objectId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));
        ResponseUserDto responseUserDto = userMapper.toDtoResponseUser(user);
        return ResponseEntity.ok(new ResponseDto(200, "El usuario ha sido encontrado exitosamente", responseUserDto));

    }

    @Override
    public ResponseEntity<?> getUsers() throws Exception {
        List<User> userList = userRepository.findAll();
        String message = userList.isEmpty() ? "No hay usuarios registrados en el sistema" : "Usuarios encontrados";
        List<ResponseUserDto> listDto = userMapper.toDTOListReponseUser(userList);
        return ResponseEntity.ok(new ResponseDto(200, message, listDto));
    }


    //metodo para cambiar la clave
    @Override
    public ResponseEntity<?> verifyEmailAndDocumentNumber(VerifyEmailAndDocumentNumberUserDto verifyEmailAndDocumentNumberUserDto) {
        Optional<User> isExisting = userRepository.findByEmailAndDocumentNumber(verifyEmailAndDocumentNumberUserDto.email(), verifyEmailAndDocumentNumberUserDto.documentNumber());
        if (isExisting.isPresent()) {
            ResponseUserDto responseUserDto = userMapper.toDtoResponseUser(isExisting.get());
            return ResponseEntity.ok(new ResponseDto(200, "Las credenciales están relacionado con un suario", responseUserDto));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseDto(400, "Las credenciales no están relacionados a un usuario registrado en el sistema", false));
        }
    }

    //cambiar clave usuario
    @Override
    public ResponseEntity<?> changeUserPassword(ChangeUserPassword changeUserPassword) throws Exception {

        Optional<User> isExisting = userRepository.findByDocumentNumber(changeUserPassword.documentNumber());
        if (isExisting.isPresent()) {
            if (!isExisting.get().getPassword().equals(changeUserPassword.password())) {
                User user = isExisting.get();
                user.setPassword(changeUserPassword.password());
                ResponseUserDto responseUserDto = userMapper.toDtoResponseUser(user);
                return ResponseEntity.ok(new ResponseDto(200, "La contraseña se ha cambiado exitosamente", responseUserDto));
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ResponseDto(409, "La contraseña debe ser diferente a la ultima registrada", false));
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseDto(400, "La cédula no coincide con la verificación", false));
        }
    }


}

