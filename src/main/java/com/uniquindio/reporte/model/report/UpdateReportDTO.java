package com.uniquindio.reporte.model.report;

import com.uniquindio.reporte.model.DTO.location.LocationDTO;

public record UpdateReportDTO(String status,
                              String title,
                              LocationDTO location,
                              String id,
                              String description) {
}
