package com.uniquindio.reporte.controllers;

import com.uniquindio.reporte.model.DTO.historyReport.CreateHistoryReportDTO;
import com.uniquindio.reporte.model.DTO.historyReport.UpdateHistoryReportDTO;
import com.uniquindio.reporte.service.HistoryReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/historyReport")
@RequiredArgsConstructor
public class HistoryReportController {

    private final HistoryReportService service;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody CreateHistoryReportDTO dto) {
        return service.createHistoryReport(dto);
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody UpdateHistoryReportDTO dto) {
        return service.updateHistoryReport(dto);
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        return service.getAllHistoryReports();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable String id) {
        return service.getHistoryReportById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        return service.deleteHistoryReport(id);
    }
}
