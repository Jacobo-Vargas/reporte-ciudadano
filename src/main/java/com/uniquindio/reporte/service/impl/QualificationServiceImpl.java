package com.uniquindio.reporte.service.impl;

import com.uniquindio.reporte.exceptions.NotFoundException;
import com.uniquindio.reporte.mapper.QualificationMapper;
import com.uniquindio.reporte.model.DTO.qualification.CreateQualificationDTO;
import com.uniquindio.reporte.model.DTO.qualification.UpdateQualificationDTO;
import com.uniquindio.reporte.model.entities.Qualification;
import com.uniquindio.reporte.model.enums.EnumStatusQualification;
import com.uniquindio.reporte.repository.QualificationRepository;
import com.uniquindio.reporte.service.QualificationService;
import com.uniquindio.reporte.utils.ObjectIdMapperUtil;
import com.uniquindio.reporte.utils.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QualificationServiceImpl implements QualificationService {

    private final QualificationRepository qualificationRepository;
    private final QualificationMapper qualificationMapper;

    @Override
    public ResponseEntity<?> createQualification(CreateQualificationDTO dto) {
        Qualification qualification = qualificationMapper.toEntity(dto);
        qualification.setDateCreation(LocalDate.now());
        qualificationRepository.save(qualification);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDto(201, "Calificación creada con éxito", qualificationMapper.toDTO(qualification)));
    }

    @Override
    public ResponseEntity<?> updateQualification(UpdateQualificationDTO dto) {
        try {
            ObjectId reportObjectId = ObjectIdMapperUtil.map(dto.reportId());
            ObjectId userObjectId = ObjectIdMapperUtil.map(dto.userId());

            Qualification qualification = qualificationRepository
                    .findByReportIdAndUserId(reportObjectId, userObjectId)
                    .orElseThrow(() -> new NotFoundException("No se encontró una calificación con ese usuario y reporte"));

            if (qualification.getStatus() == EnumStatusQualification.ELIMINADO) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ResponseDto(400, "Esta calificación ya fue eliminada", null));
            }

            qualification.setReaction(dto.reaction());
            qualification.setDateCreation(LocalDate.now());

            qualificationRepository.save(qualification);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseDto(200, "Calificación actualizada con éxito", qualificationMapper.toDTO(qualification)));
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseDto(404, e.getMessage(), null));
        }
    }

    @Override
    public ResponseEntity<?> getAllQualifications() {
        List<Qualification> list = qualificationRepository.findAll().stream()
                .filter(q -> q.getStatus() == EnumStatusQualification.ACTIVO)
                .toList();

        return ResponseEntity.ok(new ResponseDto(200, "Calificaciones obtenidas con éxito", qualificationMapper.toListDTO(list)));
    }

    @Override
    public ResponseEntity<?> getQualificationById(String id) {
        try {
            Qualification qualification = qualificationRepository.findById(ObjectIdMapperUtil.map(id))
                    .orElseThrow(() -> new NotFoundException("No se encontró la calificación con id: " + id));

            if (qualification.getStatus() == EnumStatusQualification.ELIMINADO) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ResponseDto(404, "Esta calificación está eliminada", null));
            }

            return ResponseEntity.ok(new ResponseDto(200, "Calificación obtenida", qualificationMapper.toDTO(qualification)));
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseDto(404, e.getMessage(), null));
        }
    }

    @Override
    public ResponseEntity<?> deleteQualification(String id) {
        try {
            Qualification qualification = qualificationRepository.findById(ObjectIdMapperUtil.map(id))
                    .orElseThrow(() -> new NotFoundException("No se encontró la calificación con id: " + id));

            if (qualification.getStatus() == EnumStatusQualification.ELIMINADO) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ResponseDto(400, "La calificación ya fue eliminada", null));
            }

            qualification.setStatus(EnumStatusQualification.ELIMINADO);
            qualificationRepository.save(qualification);

            return ResponseEntity.ok(new ResponseDto(200, "Calificación eliminada con éxito", null));
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseDto(404, e.getMessage(), null));
        }
    }
}