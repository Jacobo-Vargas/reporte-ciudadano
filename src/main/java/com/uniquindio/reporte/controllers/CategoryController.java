package com.uniquindio.reporte.controllers;

import com.uniquindio.reporte.model.DTO.CreateCategoryDTO;
import com.uniquindio.reporte.model.DTO.UpdateCategoryDTO;
import com.uniquindio.reporte.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    //crear categoria
    @PostMapping()
    public ResponseEntity<?> createCategory(@RequestBody @Valid CreateCategoryDTO createCategoryDTO) {
        return categoryService.createCategory(createCategoryDTO);
    }

    //actualizar categoria
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable ObjectId id, @RequestBody @Valid UpdateCategoryDTO updateCategoryDTO) {
        return categoryService.updateCategory(id, updateCategoryDTO);
    }

    //eliminar categoria
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable ObjectId id) {
        return categoryService.deleteCategory(id);
    }

    //obtener categoria por id
    @GetMapping("/{id}")
    public ResponseEntity<?> getCategory(@PathVariable ObjectId id) {
        return categoryService.getCategory(id);
    }

    // obtener todos los usuarios
    @GetMapping("")
    public ResponseEntity<?> getCategories() {
        return categoryService.getCategories();
    }


}
