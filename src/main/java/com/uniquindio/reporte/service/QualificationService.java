package com.uniquindio.reporte.service;

import com.uniquindio.reporte.model.DTO.qualification.CreateQualificationDTO;
import com.uniquindio.reporte.model.DTO.qualification.UpdateQualificationDTO;
import org.springframework.http.ResponseEntity;

public interface QualificationService {

    ResponseEntity<?> createQualification(CreateQualificationDTO dto);

    ResponseEntity<?> updateQualification(UpdateQualificationDTO dto);

    ResponseEntity<?> getAllQualifications();

    ResponseEntity<?> getQualificationById(String id);

    ResponseEntity<?> deleteQualification(String id);

}
