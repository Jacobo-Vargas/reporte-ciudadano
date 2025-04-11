package com.uniquindio.reporte.model.DTO.historyReport;

import com.uniquindio.reporte.model.enums.reports.EnumStatusReport;
import jakarta.validation.constraints.NotNull;

public record CreateHistoryReportDTO(
        @NotNull String observations,
        @NotNull String clienteId,
        @NotNull EnumStatusReport enumStatusReport
) {}