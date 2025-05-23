package com.uniquindio.reporte.model.DTO.report;

import com.uniquindio.reporte.model.DTO.location.LocationDTO;

public record GeneralReportDTO(String id,
                               String description,
                               String title,
                               String userId,
                               String categoryId,
                               String status,
                               LocationDTO location) {
}
