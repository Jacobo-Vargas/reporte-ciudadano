package com.uniquindio.reporte.service.impl;

import com.uniquindio.reporte.mapper.UserMapper;
import com.uniquindio.reporte.model.DTO.user.register.*;
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
import com.uniquindio.reporte.utils.ObjectIdMapperUtil;
import com.uniquindio.reporte.utils.OperationUtils;
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
import java.util.*;

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
    public ResponseEntity<?> sendCodeConfirmation(String email) throws Exception {
        Optional<User> isExistingUser = userRepository.findByEmail(email);
        if(isExistingUser.isPresent()) {
            // Generar código y enviarlo
            String code = verificationCodeService.generateAndStoreCode(email);
            emailService.sendCodeVerifaction(email, code);
            //guardar en la base de datos el cod
            codeValidationServiceIpml.saveCode(code, email);
            return ResponseEntity.status(HttpStatus.ACCEPTED)
                    .body(new ResponseDto(200, "Código de verificación enviado al correo electrónico: ", email));
        }else {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ResponseDto(400, "No hay un usario registrado con el email:  ", email));
        }
    }


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
        user.setVerification(false);
        ResponseUserDto responseUserDto = userMapper.toDtoResponseUser(userRepository.save(user));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDto(200, "Usuario creado exitosamente", responseUserDto));

    }

    @Override
    public ResponseEntity<?> verifyAccountEmailCode(VerifyAccountEmailCodeDto verifyAccountEmailCodeDto) throws Exception {
        Optional<CodeValidation> codeValidation = codeValidationServiceIpml.getCodeValidation(verifyAccountEmailCodeDto.code());
        Optional<User> isExistigUser = userRepository.findByEmail(verifyAccountEmailCodeDto.email());
        if (isExistigUser.isPresent()) {
            User user = isExistigUser.get();
            if (!user.isVerification()) {
                if (codeValidation.isPresent()) {
                    if (codeValidation.get().getCode().equals(verifyAccountEmailCodeDto.code())) {
                        LocalDateTime now = LocalDateTime.now();
                        LocalDateTime expiresAt = codeValidation.get().getExpiresAt();
                        if (now.isBefore(expiresAt)) {
                            user.setVerification(true);
                            userRepository.save(user);
                            codeValidationServiceIpml.deleteByCode(codeValidation.get().getCode());
                            return ResponseEntity.status(HttpStatus.ACCEPTED)
                                    .body(new ResponseDto(200, "La cuenta ha sido verificada", null));
                        } else {
                            return ResponseEntity.status(HttpStatus.CONFLICT)
                                    .body(new ResponseDto(410, "El código ha expirado", verifyAccountEmailCodeDto.code()));
                        }
                    } else {
                        return ResponseEntity.status(HttpStatus.CONFLICT)
                                .body(new ResponseDto(409, String.format("El codigo  %s  no es correcto ", verifyAccountEmailCodeDto.code()), null));
                    }
                } else {
                    return ResponseEntity.status(HttpStatus.CONFLICT)
                            .body(new ResponseDto(410, "No hay un codigo registrado con ese valor", verifyAccountEmailCodeDto.code()));
                }
            } else {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(new ResponseDto(410, "La cuenta ya se encunetra verificada", null));
            }
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ResponseDto(410, "No hay un usuario registrado con el email :", verifyAccountEmailCodeDto.email()));
        }
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
                user.setPassword(passwordEncoder.encode(changeUserPassword.password()));
                ResponseUserDto responseUserDto = userMapper.toDtoResponseUser(user);
                userRepository.save(user);
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
    @Override
    public ResponseEntity<?> addFollowerToUser(FollowerRequestDto dto) throws Exception {
        ObjectId followerdId = new ObjectId(dto.followerId());
        ObjectId userId = new ObjectId(dto.idUserLoggin());
        Optional<User> optionalUser = userRepository.findById(userId);
        Optional<User> optionalFollower = userRepository.findById(followerdId);
        // Verificar si el usuario o el seguidor no existen
        if (optionalUser.isEmpty() || optionalFollower.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Usuario o seguidor no encontrado");
        }
        User user = optionalUser.get();
        // Verificar si el seguidor ya está en la lista de seguidores del usuario
        boolean isAlreadyFollowing = user.getFollowers().stream()
                .anyMatch(followerEntry -> followerEntry.containsKey(followerdId.toString()));
        if (isAlreadyFollowing) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("El seguidor ya está en la lista de seguidores");
        }
        // Agregar nuevo seguidor con su rating
        Map<String, Integer> newFollower = new HashMap<>();
        newFollower.put(dto.followerId(), dto.rating());
        // Agregar el seguidor a la lista de seguidores
        user.getFollowers().add(newFollower);
        // Actualizar el score sumando el rating
        user.setScore(user.getScore() + dto.rating());
        // Guardar los cambios
        userRepository.save(user);
        return ResponseEntity.ok("Seguidor agregado y puntuación actualizada");
    }


    public ResponseEntity<?> removeFollowerFromUser(FollowerRequestDto dto) throws Exception {
        ObjectId followerId = new ObjectId(dto.followerId());
        ObjectId userId = new ObjectId(dto.idUserLoggin());
        Optional<User> optionalUser = userRepository.findById(userId);
        Optional<User> optionalFollower = userRepository.findById(followerId);
        if (optionalUser.isEmpty() || optionalFollower.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario o seguidor no encontrado");
        }
        User user = optionalUser.get();
        List<Map<String, Integer>> followers = user.getFollowers();
        if (followers == null || followers.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El usuario no tiene seguidores");
        }
        // Buscar el seguidor en la lista
        Map<String, Integer> followerToRemove = followers.stream()
                .filter(f -> f.containsKey(dto.followerId()))
                .findFirst()
                .orElse(null);
        if (followerToRemove == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Este usuario no es un seguidor");
        }
        // Restar la calificación del score y eliminar el seguidor
        int rating = followerToRemove.get(dto.followerId());
        user.setScore(user.getScore() - rating);
        followers.remove(followerToRemove);
        userRepository.save(user);
        return ResponseEntity.ok("Seguidor eliminado y puntaje actualizado");
    }

}