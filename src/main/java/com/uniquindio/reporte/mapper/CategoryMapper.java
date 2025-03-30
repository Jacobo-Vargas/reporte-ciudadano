package com.uniquindio.reporte.mapper;

import com.uniquindio.reporte.model.DTO.CreateCategoryDTO;
import com.uniquindio.reporte.model.DTO.CreateUserDTO;
import com.uniquindio.reporte.model.DTO.UpdateCategoryDTO;
import com.uniquindio.reporte.model.DTO.UpdateUserDto;
import com.uniquindio.reporte.model.entities.Category;
import com.uniquindio.reporte.model.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    @Mapping(target = "createdAt", expression = "java(java.time.LocalDate.now())")
    Category toDocumentCreate(CreateCategoryDTO categoryDTO);
    Category toDocumentUpdate(UpdateCategoryDTO updateCategoryDTO);
}
