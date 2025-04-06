package com.uniquindio.reporte.controllers;

import com.uniquindio.reporte.model.DTO.report.ChangeStatusReportDTO;
import com.uniquindio.reporte.model.DTO.report.CreateReportDTO;
import com.uniquindio.reporte.model.DTO.report.UpdateReportDTO;
import com.uniquindio.reporte.model.entities.Report;
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

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReportById(@PathVariable ObjectId id) {
        return reportService.deleteReportById(id);
    }


    @PatchMapping("/status")
    public ResponseEntity<?> changeStatusReport(@RequestBody ChangeStatusReportDTO changeStatusReportDTO) {
        return reportService.changeStatusReport(changeStatusReportDTO);
    }

    @GetMapping
    public ResponseEntity<?> getAllReports() {
        return reportService.getReport();
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveReport(@RequestBody Report report) {
        return reportService.saveReport(report);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getReportById(@PathVariable ObjectId id) {
        return reportService.getReportById(id);
    }


    @GetMapping("/status/{isActive}")
    public ResponseEntity<?> getAllReportsByStatus(@PathVariable boolean isActive) {
        return reportService.getAllReportsByStatus(isActive);
    }

    //
    //  cambiar estado  de usuario
    @PutMapping("/{id}/estado/{idAdmin}")
    public ResponseEntity<?> changeUserStatusReportAdmin(@PathVariable String id, @RequestParam String estado, @PathVariable String idAdmin) throws Exception {
        return reportService.changeUserStatusReportAdmin(id,estado,idAdmin);
    }
}


