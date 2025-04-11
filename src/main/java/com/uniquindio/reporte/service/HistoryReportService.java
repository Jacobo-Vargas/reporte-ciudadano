package com.uniquindio.reporte.service;

import com.uniquindio.reporte.model.DTO.historyReport.CreateHistoryReportDTO;
import com.uniquindio.reporte.model.DTO.historyReport.UpdateHistoryReportDTO;
import org.springframework.http.ResponseEntity;

public interface HistoryReportService {

    ResponseEntity<?> createHistoryReport(CreateHistoryReportDTO dto);

    ResponseEntity<?> updateHistoryReport(UpdateHistoryReportDTO dto);

    ResponseEntity<?> getAllHistoryReports();

    ResponseEntity<?> getHistoryReportById(String id);

    ResponseEntity<?> deleteHistoryReport(String id);
}
