package com.uniquindio.reporte.service;


import com.uniquindio.reporte.model.DTO.category.CreateCategoryDTO;
import com.uniquindio.reporte.model.DTO.category.UpdateCategoryDTO;
import org.springframework.http.ResponseEntity;

public interface CategoryService {

    ResponseEntity<?> createCategory(CreateCategoryDTO createCategoryDTO);


    ResponseEntity<?> updateCategory(String name, UpdateCategoryDTO updateCategoryDTO);

    ResponseEntity<?> deleteCategory(String name);

    ResponseEntity<?> getCategory(String name);

    ResponseEntity<?> getCategories();
}
