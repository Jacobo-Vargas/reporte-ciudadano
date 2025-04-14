package com.uniquindio.reporte.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.uniquindio.reporte.exceptions.NotFoundException;
import com.uniquindio.reporte.model.DTO.report.ChangeStatusReportDTO;
import com.uniquindio.reporte.model.DTO.report.CreateReportDTO;
import com.uniquindio.reporte.model.DTO.report.UpdateReportDTO;
import com.uniquindio.reporte.model.enums.reports.EnumStatusReport;
import com.uniquindio.reporte.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/report")
public class ReportController {

    private final ReportService reportService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createReport(@RequestParam("createReportDTO") String createReportDTO,
                                          @RequestParam(value = "photos", required = false) List<MultipartFile> photos) throws IOException {
        CreateReportDTO createReport = new ObjectMapper().readValue(createReportDTO, CreateReportDTO.class);

        return reportService.createReport(createReport, photos);
    }

    @PutMapping
    public ResponseEntity<?> updateReport(@RequestBody UpdateReportDTO updateReportDTO) {
        return reportService.updateReport(updateReportDTO);
    }

    @PatchMapping("/status")
    public ResponseEntity<?> changeStatusReport(@RequestBody ChangeStatusReportDTO changeStatusReportDTO) {
        return reportService.changeStatusReport(changeStatusReportDTO);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllReports() {
        return reportService.getReport();
    }


    @GetMapping()
    public ResponseEntity<?> getReportById(@RequestParam String id) {
        return reportService.getReportById(id);
    }

    @DeleteMapping()
    public ResponseEntity<?> deleteReportById(@RequestParam String id) {
        return reportService.deleteReportById(id);
    }

    @GetMapping("/status")
    public ResponseEntity<?> getAllReportsByStatus(@RequestParam String status) {
        return reportService.getAllReportsByStatus(EnumStatusReport.valueOf(status));
    }

    @PutMapping("/markImportant")
    public ResponseEntity<?> markAsImportant(@RequestParam String reportId, @RequestParam boolean isImportant) throws NotFoundException {
        return reportService.markAsImportant(reportId, isImportant);
    }
}


