package com.uniquindio.reporte.mapper;

import com.uniquindio.reporte.model.DTO.report.GeneralReportDTO;
import com.uniquindio.reporte.model.entities.Report;
import com.uniquindio.reporte.utils.ObjectIdMapperUtil;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ObjectIdMapperUtil.class})
public interface ReportMapper {


    Report toEntity(GeneralReportDTO generalReportDTO);

    GeneralReportDTO toDTO(Report report);

    List<GeneralReportDTO> toListDTO (List<Report> listReport);
}
