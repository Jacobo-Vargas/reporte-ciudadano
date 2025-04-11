package com.uniquindio.reporte.service;

import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;

public interface ReportStatisticsService {
    ResponseEntity<?> getReportsByCategory(String categoryId);
    ResponseEntity<?> getReportsByStatus(String status);
    ResponseEntity<?> getReportsByDateRange(LocalDate startDate, LocalDate endDate);
}

