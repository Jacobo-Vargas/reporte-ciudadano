package com.uniquindio.reporte.model.DTO.historyReport;

import com.uniquindio.reporte.model.enums.reports.EnumStatusReport;

public record GeneralHistoryReportDTO(
        String id,
        String observations,
        String clienteId,
        String date,
        EnumStatusReport enumStatusReport
) {}