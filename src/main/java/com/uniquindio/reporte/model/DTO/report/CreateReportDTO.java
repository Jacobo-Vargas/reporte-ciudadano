package com.uniquindio.reporte.model.DTO.report;

import com.uniquindio.reporte.model.DTO.location.LocationDTO;

public record CreateReportDTO(String description,
                              String userId,
                              String title,
                              String categoryId,
                              LocationDTO location) {
}
