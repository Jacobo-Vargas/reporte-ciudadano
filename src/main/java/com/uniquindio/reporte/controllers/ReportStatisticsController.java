package com.uniquindio.reporte.controllers;

import com.uniquindio.reporte.service.ReportStatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/statistics")
@RequiredArgsConstructor
public class ReportStatisticsController {

    private final ReportStatisticsService reportStatisticsService;

    @GetMapping("/by-category")
    public ResponseEntity<?> getReportsByCategory(@RequestParam String categoryId) {
        return reportStatisticsService.getReportsByCategory(categoryId);
    }

    @GetMapping("/by-status")
    public ResponseEntity<?> getReportsByStatus(@RequestParam String status) {
        return reportStatisticsService.getReportsByStatus(status);
    }

    @GetMapping("/by-date-range")
    public ResponseEntity<?> getReportsByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return reportStatisticsService.getReportsByDateRange(startDate, endDate);
    }
}
