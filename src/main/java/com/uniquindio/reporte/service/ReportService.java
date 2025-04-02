package com.uniquindio.reporte.service;

import com.uniquindio.reporte.model.DTO.report.ChangeStatusReportDTO;
import com.uniquindio.reporte.model.DTO.report.CreateReportDTO;
import com.uniquindio.reporte.model.DTO.report.UpdateReportDTO;
import com.uniquindio.reporte.model.entities.Report;
import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;

public interface ReportService {

    ResponseEntity<?> createReport(CreateReportDTO createReportDTO);

    ResponseEntity<?> updateReport(UpdateReportDTO updateReportDTO);

    ResponseEntity<?> changeStatusReport(ChangeStatusReportDTO changeStatusReportDTO);

    ResponseEntity<?> getReport();

    ResponseEntity<?> saveReport(Report report);

    ResponseEntity<?> getReportById(ObjectId id);

    ResponseEntity<?> deleteReportById(ObjectId id);

    ResponseEntity<?> getAllReportsByStatus(boolean isActive);
}
