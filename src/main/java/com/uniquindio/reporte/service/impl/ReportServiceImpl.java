package com.uniquindio.reporte.service.impl;

import com.uniquindio.reporte.mapper.ReportMapper;
import com.uniquindio.reporte.model.DTO.report.ChangeStatusReportDTO;
import com.uniquindio.reporte.model.DTO.report.CreateReportDTO;
import com.uniquindio.reporte.model.DTO.report.GeneralReportDTO;
import com.uniquindio.reporte.model.DTO.report.UpdateReportDTO;
import com.uniquindio.reporte.model.entities.Report;
import com.uniquindio.reporte.repository.ReportRepository;
import com.uniquindio.reporte.service.ReportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final ReportRepository reportRepository;
    private final ReportMapper reportMapper;

    @Override
    public ResponseEntity<?> createReport(CreateReportDTO createReportDTO) {
        return null;
    }

    @Override
    public ResponseEntity<?> updateReport(UpdateReportDTO updateReportDTO) {
        return null;
    }

    @Override
    public ResponseEntity<?> changeStatusReport(ChangeStatusReportDTO changeStatusReportDTO) {
        return null;
    }

    @Override
    public ResponseEntity<?> getReport() {
        List<Report> listR = reportRepository.findAll();
        List<GeneralReportDTO> list = reportMapper.toListDTO(listR);
        return ResponseEntity.ok(list);
    }

    @Override
    public ResponseEntity<?> saveReport(Report report) {
        return null;
    }

    @Override
    public ResponseEntity<?> getReportById(ObjectId id) {
        return null;
    }

    @Override
    public ResponseEntity<?> deleteReportById(ObjectId id) {
        return null;
    }

    @Override
    public ResponseEntity<?> getAllReportsByStatus(boolean isActive) {
        return null;
    }
}
