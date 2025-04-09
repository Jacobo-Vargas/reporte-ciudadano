package com.uniquindio.reporte.mapper;

import com.uniquindio.reporte.model.DTO.category.CreateCategoryDTO;
import com.uniquindio.reporte.model.DTO.category.GeneralCategoryDTO;
import com.uniquindio.reporte.model.DTO.category.UpdateCategoryDTO;
import com.uniquindio.reporte.model.entities.Category;
import com.uniquindio.reporte.utils.ObjectIdMapperUtil;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ObjectIdMapperUtil.class, LocationMapper.class})
public interface CategoryMapper {

    @Mapping(target = "createdAt", expression = "java(java.time.LocalDate.now())")
    Category toEntity(CreateCategoryDTO categoryDTO);
    GeneralCategoryDTO toDTO(Category category);
    List<GeneralCategoryDTO> toListDTO (List<Category> listReport);
    Category toDocumentUpdate(UpdateCategoryDTO updateCategoryDTO);
}
