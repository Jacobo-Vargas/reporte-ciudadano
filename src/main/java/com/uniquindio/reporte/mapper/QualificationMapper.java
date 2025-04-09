package com.uniquindio.reporte.mapper;

import com.uniquindio.reporte.model.DTO.qualification.CreateQualificationDTO;
import com.uniquindio.reporte.model.DTO.qualification.GeneralQualificationDTO;
import com.uniquindio.reporte.model.DTO.qualification.UpdateQualificationDTO;
import com.uniquindio.reporte.model.entities.Qualification;
import com.uniquindio.reporte.utils.ObjectIdMapperUtil;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ObjectIdMapperUtil.class, LocationMapper.class})
public interface QualificationMapper {

    @Mapping(target = "dateCreation", expression = "java(java.time.LocalDate.now())")
    Qualification toEntity(CreateQualificationDTO dto);
    GeneralQualificationDTO toDTO(Qualification qualification);
    List<GeneralQualificationDTO> toListDTO(List<Qualification> qualifications);
}
