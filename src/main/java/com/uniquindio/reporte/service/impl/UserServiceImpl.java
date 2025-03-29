package com.uniquindio.reporte.service.impl;

import com.uniquindio.reporte.mapper.UserMapper;
import com.uniquindio.reporte.model.DTO.CreateUserDTO;
import com.uniquindio.reporte.model.entities.User;
import com.uniquindio.reporte.repository.UserRepository;
import com.uniquindio.reporte.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final MongoTemplate mongoTemplate;

    @Override
    public User createUser(CreateUserDTO userDTO) throws Exception {

        if (existeEmail(userDTO.email())) {
            throw new IllegalArgumentException("El email ya está registrado");
        }

        User user = userMapper.toDocument(userDTO);
        return userRepository.save(user);
    }

    private boolean existeEmail(String email) {
        return userRepository.findByEmail(email).isPresent();
    }
}

