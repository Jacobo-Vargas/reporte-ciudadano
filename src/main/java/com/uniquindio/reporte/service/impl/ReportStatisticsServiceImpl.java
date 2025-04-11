package com.uniquindio.reporte.service.impl;

import com.itextpdf.text.DocumentException;
import com.uniquindio.reporte.mapper.ReportMapper;
import com.uniquindio.reporte.model.entities.Report;

import com.itextpdf.text.DocumentException;
import com.uniquindio.reporte.mapper.ReportMapper;
import com.uniquindio.reporte.model.entities.Report;
import com.uniquindio.reporte.repository.ReportRepository;
import com.uniquindio.reporte.service.ReportStatisticsService;
import com.uniquindio.reporte.utils.PdfReportGenerator;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportStatisticsServiceImpl implements ReportStatisticsService {

    private final ReportRepository reportRepository;
    private final ReportMapper reportMapper;

    @Override
    public ResponseEntity<?> getReportsByCategory(String categoryId) {
        List<Report> reports = reportRepository.findByCategoryId(new ObjectId(categoryId));

        List<String> lines = reports.stream()
                .map(this::describeReport)
                .collect(Collectors.toList());

        try {
            ByteArrayInputStream pdf = PdfReportGenerator.generate("Reporte por Categoría", lines);
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "inline; filename=reportes-categoria.pdf");

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(new InputStreamResource(pdf));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Error generando el PDF: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> getReportsByStatus(String status) {
        List<Report> reports = reportRepository.findByStatus(status);

        List<String> lines = reports.stream()
                .map(this::describeReport)
                .collect(Collectors.toList());

        // Debug
        lines.forEach(System.out::println);

        try {
            ByteArrayInputStream pdf = PdfReportGenerator.generate("Reporte por Estado", lines);
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "inline; filename=reportes-estado.pdf");

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(new InputStreamResource(pdf));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Error generando el PDF: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> getReportsByDateRange(LocalDate startDate, LocalDate endDate) {
        System.out.println(">>> [SERVICE] Entró al servicio getReportsByDateRange");
        System.out.println(">>> [SERVICE] Fecha inicio: " + startDate);
        System.out.println(">>> [SERVICE] Fecha fin: " + endDate);

        List<Report> reports = reportRepository.findAll().stream()
                .filter(report -> {
                    if (report.getDateCreation() == null) {
                        System.out.println(">>> [WARN] Report con dateCreation nulo: " + report.getId());
                        return false;
                    }
                    return !report.getDateCreation().isBefore(startDate) && !report.getDateCreation().isAfter(endDate);
                })
                .toList();

        List<String> lines = reports.stream()
                .map(this::describeReport)
                .collect(Collectors.toList());

        try {
            ByteArrayInputStream pdf = PdfReportGenerator.generate("Reporte por Rango de Fechas", lines);
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "inline; filename=reportes-fechas.pdf");

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(new InputStreamResource(pdf));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Error generando el PDF: " + e.getMessage());
        }
    }


    private String describeReport(Report report) {
        return "ID: " + safe(report.getId()) +
                "\nTítulo: " + safe(report.getTitle()) +
                "\nDescripción: " + safe(report.getDescription()) +
                "\nEstado: " + safe(report.getStatus()) +
                "\nUsuario ID: " + safe(report.getUserId()) +
                "\nCategoría ID: " + safe(report.getCategoryId()) +
                "\nFecha de creación: " + (report.getDateCreation() != null ? report.getDateCreation() : "No disponible") +
                "\n";
    }

    private String safe(Object value) {
        return value != null ? value.toString() : "No disponible";
    }
}
