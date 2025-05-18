package com.uniquindio.reporte.service.impl;

import com.uniquindio.reporte.exceptions.NotFoundException;
import com.uniquindio.reporte.mapper.CategoryMapper;
import com.uniquindio.reporte.model.DTO.category.CreateCategoryDTO;
import com.uniquindio.reporte.model.DTO.category.GeneralCategoryDTO;
import com.uniquindio.reporte.model.DTO.category.UpdateCategoryDTO;
import com.uniquindio.reporte.model.entities.Category;
import com.uniquindio.reporte.model.enums.EnumStatusCategory;
import com.uniquindio.reporte.model.enums.users.EnumUserType;
import com.uniquindio.reporte.repository.CategoryRepository;
import com.uniquindio.reporte.service.CategoryService;
import com.uniquindio.reporte.utils.OperationUtils;
import com.uniquindio.reporte.utils.ResponseDto;
import lombok.RequiredArgsConstructor;
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
        Category category = categoryMapper.toEntity(createCategoryDTO);

        boolean flag = OperationUtils.validateUserByRol(EnumUserType.ADMINISTRADOR.name());
        if (!flag) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseDto(200, "No tiene permisos suficientes.", null));

        }

        try {
            categoryRepository.save(category);
        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseDto(HttpStatus.BAD_REQUEST.value(), "Error interno, no se pudo guardar la categoría", null));

        }

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDto(201, "Categoría creada correctamente", categoryMapper.toDTO(category)));
    }

    @Override
    public ResponseEntity<?> updateCategory(String name, UpdateCategoryDTO updateCategoryDTO) {
        Optional<Category> optionalCategory = categoryRepository.findByName(name)
                .filter(c -> c.getStatus() != EnumStatusCategory.ELIMINADO);

        boolean flag = OperationUtils.validateUserByRol(EnumUserType.ADMINISTRADOR.name());

        if (!flag) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseDto(200, "No tiene permisos suficientes.", null));

        }
        if (optionalCategory.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseDto(404, "Categoría no encontrada o eliminada", null));
        }

        Category category = optionalCategory.get();
        if (updateCategoryDTO.name() != null) category.setName(updateCategoryDTO.name());
        if (updateCategoryDTO.icon() != null) category.setIcon(updateCategoryDTO.icon());
        if (updateCategoryDTO.description() != null) category.setDescription(updateCategoryDTO.description());

        categoryRepository.save(category);
        return ResponseEntity.ok(new ResponseDto(200, "Categoría actualizada correctamente", categoryMapper.toDTO(category)));
    }

    @Override
    public ResponseEntity<?> deleteCategory(String name) {
        Optional<Category> optionalCategory = categoryRepository.findByName(name);
        if (optionalCategory.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseDto(404, "Categoría no encontrada", null));
        }

        boolean flag = OperationUtils.validateUserByRol(EnumUserType.ADMINISTRADOR.name());
        if (!flag) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseDto(200, "No tiene permisos suficientes.", null));

        }

        try {
            Category category = optionalCategory.get();
            category.setStatus(EnumStatusCategory.ELIMINADO);
            categoryRepository.save(category);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseDto(HttpStatus.BAD_REQUEST.value(), "Error interno, no se pudo eliminar lógicamente", null));
        }

        return ResponseEntity.ok(new ResponseDto(200, "Categoría eliminada correctamente", null));
    }

    @Override
    public ResponseEntity<?> getCategory(String name) {
        Optional<Category> category = categoryRepository.findByName(name)
                .filter(c -> c.getStatus() != EnumStatusCategory.ELIMINADO);

        return category.map(value -> ResponseEntity.ok(new ResponseDto(200, "Categoría encontrada", value)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ResponseDto(404, "Categoría no encontrada", null)));
    }

    @Override
    public ResponseEntity<?> getCategories() {
        List<Category> categories = categoryRepository.findAll()
                .stream()
                .filter(c -> c.getStatus() == EnumStatusCategory.ACTIVO)
                .toList();

        if (categories.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        List<GeneralCategoryDTO> categoriesR = categoryMapper.toListDTO(categories);
        return ResponseEntity.ok(new ResponseDto(HttpStatus.OK.value(), "Lista de categorías obtenida", categoriesR));
    }


    @Override
    public ResponseEntity<?> getCategoriesByStatus(String status) {
        List<Category> categories = categoryRepository.findAll()
                .stream()
                .filter(c -> c.getStatus().name().equals(status))
                .toList();

        if (categories.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        List<GeneralCategoryDTO> categoriesR = categoryMapper.toListDTO(categories);
        return ResponseEntity.ok(new ResponseDto(HttpStatus.OK.value(), "Lista de categorías obtenida", categoriesR));

    }

    @Override
    public Category getCategoryById(String categoryId) throws NotFoundException {
        return categoryRepository.findById(categoryId).orElseThrow(() -> new NotFoundException("No existe la categoria con id ".concat(categoryId)));
    }
}