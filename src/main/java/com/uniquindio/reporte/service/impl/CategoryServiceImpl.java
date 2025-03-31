package com.uniquindio.reporte.service.impl;

import com.uniquindio.reporte.mapper.CategoryMapper;
import com.uniquindio.reporte.model.DTO.CreateCategoryDTO;
import com.uniquindio.reporte.model.DTO.UpdateCategoryDTO;
import com.uniquindio.reporte.model.entities.Category;
import com.uniquindio.reporte.repository.CategoryRepository;
import com.uniquindio.reporte.service.CategoryService;
import com.uniquindio.reporte.utils.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryMapper categoryMapper;
    private final CategoryRepository categoryRepository;

    @Override
    public ResponseEntity<?> createCategory(CreateCategoryDTO createCategoryDTO) {
        if (categoryRepository.findByName(createCategoryDTO.name()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ResponseDto(409, "La categoría ya existe", null));
        }

        Category category = categoryMapper.toDocumentCreate(createCategoryDTO);
        categoryRepository.save(category);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDto(201, "Categoría creada correctamente", category));
    }

    @Override
    public ResponseEntity<?> updateCategory(String name, UpdateCategoryDTO updateCategoryDTO) {
        Optional<Category> optionalCategory = categoryRepository.findByName(name);
        if (optionalCategory.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseDto(404, "Categoría no encontrada", null));
        }

        Category category = optionalCategory.get();
        if (updateCategoryDTO.name() != null) category.setName(updateCategoryDTO.name());
        if (updateCategoryDTO.icon() != null) category.setIcon(updateCategoryDTO.icon());
        if (updateCategoryDTO.description() != null) category.setDescription(updateCategoryDTO.description());

        categoryRepository.save(category);
        return ResponseEntity.ok(new ResponseDto(200, "Categoría actualizada correctamente", category));
    }

    @Override
    public ResponseEntity<?> deleteCategory(String name) {
        if (!categoryRepository.existsByName(name)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseDto(404, "Categoría no encontrada", null));
        }
        categoryRepository.deleteByName(name);
        return ResponseEntity.ok(new ResponseDto(200, "Categoría eliminada correctamente", null));
    }

    @Override
    public ResponseEntity<?> getCategory(String name) {
        Optional<Category> category = categoryRepository.findByName(name);
        return category.map(value -> ResponseEntity.ok(new ResponseDto(200, "Categoría encontrada", value)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ResponseDto(404, "Categoría no encontrada", null)));
    }

    @Override
    public ResponseEntity<?> getCategories() {
        List<Category> categories = categoryRepository.findAll();
        if (categories.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(new ResponseDto(200, "Lista de categorías obtenida", categories));
    }
}