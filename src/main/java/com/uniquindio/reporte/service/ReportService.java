package com.uniquindio.reporte.service;

import com.uniquindio.reporte.exceptions.NotFoundException;
import com.uniquindio.reporte.model.DTO.report.ChangeStatusReportDTO;
import com.uniquindio.reporte.model.DTO.report.CreateReportDTO;
import com.uniquindio.reporte.model.DTO.report.UpdateReportDTO;
import com.uniquindio.reporte.model.entities.Report;
import com.uniquindio.reporte.model.enums.reports.EnumStatusReport;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ReportService {

    ResponseEntity<?> createReport(CreateReportDTO createReportDTO, List<MultipartFile> photos) throws IOException;

    ResponseEntity<?> updateReport(UpdateReportDTO updateReportDTO);

    ResponseEntity<?> changeStatusReport(ChangeStatusReportDTO changeStatusReportDTO);

    ResponseEntity<?> getReport();

    void saveReport(Report report);

    ResponseEntity<?> getReportById(String id);

    ResponseEntity<?> deleteReportById(String id);

    ResponseEntity<?> getAllReportsByStatus(EnumStatusReport statusReport);

    ResponseEntity<?> markAsImportant(String reportId, boolean isImportant) throws NotFoundException;
}
