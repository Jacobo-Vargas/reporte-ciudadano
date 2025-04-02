package com.uniquindio.reporte.mapper;

import com.uniquindio.reporte.model.DTO.report.GeneralReportDTO;
import com.uniquindio.reporte.model.entities.Report;
import com.uniquindio.reporte.utils.ObjectIdMapperUtil;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {ObjectIdMapperUtil.class})
public interface ReportMapper {

    Report toEntity(GeneralReportDTO generalReportDTO);

    GeneralReportDTO toDTO(Report report);
}
