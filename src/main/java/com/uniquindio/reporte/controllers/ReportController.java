package com.uniquindio.reporte.controllers;

import com.uniquindio.reporte.model.DTO.report.ChangeStatusReportDTO;
import com.uniquindio.reporte.model.DTO.report.CreateReportDTO;
import com.uniquindio.reporte.model.DTO.report.UpdateReportDTO;
import com.uniquindio.reporte.model.entities.Report;
import com.uniquindio.reporte.model.enums.reports.EnumStatusReport;
import com.uniquindio.reporte.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/report")
public class ReportController {

    private final ReportService reportService;

    @PostMapping
    public ResponseEntity<?> createReport(@RequestBody CreateReportDTO createReportDTO) {
        return reportService.createReport(createReportDTO);
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
    //
    //  cambiar estado  de usuario
    @PutMapping("/{id}/{estado}/{idAdmin}")
    public ResponseEntity<?> changeUserStatusReportAdmin(@PathVariable String id, @PathVariable String estado, @PathVariable String idAdmin) throws Exception {
        return reportService.changeUserStatusReportAdmin(id,estado,idAdmin);
    }
}


