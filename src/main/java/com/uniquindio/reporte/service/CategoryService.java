package com.uniquindio.reporte.service;


import com.uniquindio.reporte.model.DTO.CreateCategoryDTO;
import com.uniquindio.reporte.model.DTO.CreateUserDTO;
import com.uniquindio.reporte.model.DTO.UpdateCategoryDTO;
import com.uniquindio.reporte.model.DTO.UpdateUserDto;
import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;

public interface CategoryService {

    ResponseEntity<?> createCategory(CreateCategoryDTO createCategoryDTO);

    ResponseEntity<?> updateCategory(ObjectId Id, UpdateCategoryDTO updateCategoryDTO);

    ResponseEntity<?>deleteCategory(ObjectId Id);

    ResponseEntity<?>getCategory(ObjectId Id);

    ResponseEntity<?>getCategories();

}
