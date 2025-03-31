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
    @PutMapping("/{name}")
    public ResponseEntity<?> updateCategory(@PathVariable String name, @RequestBody @Valid UpdateCategoryDTO updateCategoryDTO) {
        return categoryService.updateCategory(name, updateCategoryDTO);
    }

    //eliminar categoria
    @DeleteMapping("/{name}")
    public ResponseEntity<?> deleteCategory(@PathVariable String name) {
        return categoryService.deleteCategory(name);
    }

    //obtener categoria por id
    @GetMapping("/{name}")
    public ResponseEntity<?> getCategory(@PathVariable String name) {
        return categoryService.getCategory(name);
    }

    // obtener todos los usuarios
    @GetMapping()
    public ResponseEntity<?> getCategories() {
        return categoryService.getCategories();
    }


}
