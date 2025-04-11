package com.uniquindio.reporte.model.DTO.historyReport;

import com.uniquindio.reporte.model.enums.reports.EnumStatusReport;

public record UpdateHistoryReportDTO(String id,
                                     String observations,
                                     String clienteId,
                                     EnumStatusReport enumStatusReport
) {}