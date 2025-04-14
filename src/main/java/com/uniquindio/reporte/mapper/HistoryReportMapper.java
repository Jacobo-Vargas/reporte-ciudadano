package com.uniquindio.reporte.mapper;

import com.uniquindio.reporte.model.DTO.historyReport.CreateHistoryReportDTO;
import com.uniquindio.reporte.model.DTO.historyReport.GeneralHistoryReportDTO;
import com.uniquindio.reporte.model.entities.HistoryReport;
import com.uniquindio.reporte.utils.ObjectIdMapperUtil;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = ObjectIdMapperUtil.class)
public interface HistoryReportMapper {

    @Mapping(target = "date", expression = "java(java.time.LocalDate.now())")
    @Mapping(target = "clienteId", expression = "java(ObjectIdMapperUtil.map(dto.clienteId()))")
    @Mapping(target = "reportId", expression = "java(ObjectIdMapperUtil.map(dto.reportId()))")
    @Mapping(target = "status", constant = "ACTIVO")
    HistoryReport toEntity(CreateHistoryReportDTO dto);

    GeneralHistoryReportDTO toDTO(HistoryReport entity);

    List<GeneralHistoryReportDTO> toListDTO(List<HistoryReport> entities);
}
