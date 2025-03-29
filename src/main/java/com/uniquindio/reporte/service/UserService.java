package com.uniquindio.reporte.service;

import com.uniquindio.reporte.model.DTO.CreateUserDTO;
import com.uniquindio.reporte.model.entities.User;
import org.apache.catalina.startup.ClassLoaderFactory;
import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;

public interface UserService {

    ResponseEntity<?> createUser(CreateUserDTO createUserDTO);

    ResponseEntity<?> updateUser();

    ResponseEntity<?>changeUserStatus(ObjectId userId, String estado);

    ResponseEntity<?>getUser(ObjectId userId);

    ResponseEntity<?>getUsers();


}
