package com.uniquindio.reporte;

import com.uniquindio.reporte.mapper.CategoryMapper;
import com.uniquindio.reporte.model.DTO.category.CreateCategoryDTO;
import com.uniquindio.reporte.model.DTO.category.UpdateCategoryDTO;
import com.uniquindio.reporte.model.entities.Category;
import com.uniquindio.reporte.model.enums.EnumStatusCategory;
import com.uniquindio.reporte.repository.CategoryRepository;
import com.uniquindio.reporte.service.impl.CategoryServiceImpl;
import com.uniquindio.reporte.utils.OperationUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Mock
    private CategoryMapper categoryMapper;

    @Mock
    private CategoryRepository categoryRepository;

    private CreateCategoryDTO createCategoryDTO;
    private UpdateCategoryDTO updateCategoryDTO;
    private Category category;

    @BeforeEach
    void setUp() {
        createCategoryDTO = new CreateCategoryDTO("Pruebas", "icono.png", "Descripción");
        updateCategoryDTO = new UpdateCategoryDTO("Nueva", "nuevoicono.png", "Nueva descripción");
        category = new Category();
        category.setName("Pruebas");
        category.setStatus(EnumStatusCategory.ACTIVO);
    }

    @Test
    void createCategory_ShouldReturnCreated() {
        when(categoryMapper.toEntity(createCategoryDTO)).thenReturn(category);
        try (var mock = mockStatic(OperationUtils.class)) {
            mock.when(() -> OperationUtils.validateUserByRol(anyString())).thenReturn(true);
            when(categoryRepository.save(any(Category.class))).thenReturn(category);

            ResponseEntity<?> response = categoryService.createCategory(createCategoryDTO);
            assertEquals(201, response.getStatusCodeValue());
        }
    }

    @Test
    void updateCategory_ShouldReturnUpdated() {
        when(categoryRepository.findByName("Pruebas")).thenReturn(Optional.of(category));
        try (var mock = mockStatic(OperationUtils.class)) {
            mock.when(() -> OperationUtils.validateUserByRol(anyString())).thenReturn(true);

            ResponseEntity<?> response = categoryService.updateCategory("Pruebas", updateCategoryDTO);
            assertEquals(200, response.getStatusCodeValue());
        }
    }

    @Test
    void deleteCategory_ShouldReturnDeleted() {
        when(categoryRepository.findByName("Pruebas")).thenReturn(Optional.of(category));
        try (var mock = mockStatic(OperationUtils.class)) {
            mock.when(() -> OperationUtils.validateUserByRol(anyString())).thenReturn(true);

            ResponseEntity<?> response = categoryService.deleteCategory("Pruebas");
            assertEquals(200, response.getStatusCodeValue());
        }
    }
}

