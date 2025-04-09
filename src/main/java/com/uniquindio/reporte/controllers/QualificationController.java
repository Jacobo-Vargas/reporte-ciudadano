package com.uniquindio.reporte.controllers;

import com.uniquindio.reporte.model.DTO.qualification.CreateQualificationDTO;
import com.uniquindio.reporte.model.DTO.qualification.UpdateQualificationDTO;
import com.uniquindio.reporte.model.DTO.report.UpdateReportDTO;
import com.uniquindio.reporte.service.QualificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/qualification")
@RequiredArgsConstructor
public class QualificationController {

    private final QualificationService qualificationService;

    @PostMapping
    public ResponseEntity<?> createQualification(@RequestBody @Valid CreateQualificationDTO dto) {
        return qualificationService.createQualification(dto);
    }

    @PutMapping
    public ResponseEntity<?> updateQualification(@RequestBody UpdateQualificationDTO updateQualificationDTO) {
        return qualificationService.updateQualification(updateQualificationDTO);
    }

    @DeleteMapping
    public ResponseEntity<?> delete(@RequestParam String id) {
        return qualificationService.deleteQualification(id);
    }

    @GetMapping
    public ResponseEntity<?> getById(@RequestParam String id) {
        return qualificationService.getQualificationById(id);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAll() {
        return qualificationService.getAllQualifications();
    }
}
