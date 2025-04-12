package com.uniquindio.reporte.service.impl;

import com.uniquindio.reporte.exceptions.NotFoundException;
import com.uniquindio.reporte.mapper.ReportMapper;
import com.uniquindio.reporte.model.DTO.report.ChangeStatusReportDTO;
import com.uniquindio.reporte.model.DTO.report.CreateReportDTO;
import com.uniquindio.reporte.model.DTO.report.GeneralReportDTO;
import com.uniquindio.reporte.model.DTO.report.UpdateReportDTO;
import com.uniquindio.reporte.model.entities.Report;
import com.uniquindio.reporte.model.entities.User;
import com.uniquindio.reporte.model.enums.reports.EnumStatusReport;
import com.uniquindio.reporte.model.enums.users.EnumUserType;
import com.uniquindio.reporte.repository.ReportRepository;
import com.uniquindio.reporte.repository.UserRepository;
import com.uniquindio.reporte.service.EmailService;
import com.uniquindio.reporte.service.ReportService;
import com.uniquindio.reporte.utils.ObjectIdMapperUtil;
import com.uniquindio.reporte.utils.OperationUtils;
import com.uniquindio.reporte.utils.ResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final ReportRepository reportRepository;
    private final ReportMapper reportMapper;
    private final LocationServiceImpl locationService;
    private final UserRepository userRepository;
    private final EmailService emailService;

    @Override
    public ResponseEntity<?> createReport(CreateReportDTO createReportDTO) {

        Report report = reportMapper.toEntity(createReportDTO);

        report.setStatus(EnumStatusReport.PENDIENTE);
        report.setDateCreation(LocalDate.now());
        report.setLocation(locationService.saveLocation(createReportDTO.location()));

        saveReport(report);

        User creador = userRepository.findById(report.getUserId()).orElse(null);
        if (creador != null) {
            emailService.enviarConfirmacionCreacionReporte(
                    creador.getEmail(),
                    report.getTitle()
            );
        }


        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDto(200, "Reporte guardado con éxito", reportMapper.toDTO(report)));
    }

    @Override
    public ResponseEntity<?> updateReport(UpdateReportDTO updateReportDTO) {

        try {
            Report report = reportRepository.findById(new ObjectId(updateReportDTO.id())).orElseThrow(() -> new  NotFoundException("No se encontró un reporte con id : ".concat(updateReportDTO.id())));

            report.setStatus(EnumStatusReport.valueOf(updateReportDTO.status()));
            report.setLocation(locationService.saveLocation(updateReportDTO.location()));
            report.setTitle(updateReportDTO.title());
            report.setDescription(updateReportDTO.description());

            saveReport(report);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseDto(200, "Reporte actualizado con éxito", reportMapper.toDTO(report)));

        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseDto(404, e.getMessage(), null));
        }
     }

    @Override
    public ResponseEntity<?> changeStatusReport(ChangeStatusReportDTO changeStatusReportDTO) {
        try {

            boolean flag = validateChangeStatus(changeStatusReportDTO.status());

            if (flag) {
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new ResponseDto(200, "No tiene permisos suficientes para cambiar el estado de el reporte.", null));

            }

            Report report = reportRepository.findById(ObjectIdMapperUtil.map(changeStatusReportDTO.id())).orElseThrow(() -> new  NotFoundException("No se encontró un reporte con id : ".concat(String.valueOf(changeStatusReportDTO.status()))));
            report.setStatus(EnumStatusReport.valueOf(changeStatusReportDTO.status()));
            log.info("Actualizando reporte a nuevo estado {}", report.getStatus().name());
            saveReport(report);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseDto(200, "Reporte actualizado con éxito.", reportMapper.toDTO(report)));

        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseDto(404, e.getMessage(), null));

        }
    }

    @Override
    public ResponseEntity<?> getReport() {
        List<Report> listR = reportRepository.findAll();
        List<GeneralReportDTO> list = reportMapper.toListDTO(listR);
        return ResponseEntity.ok(list);
    }

    @Override
    public void saveReport(Report report) {
        reportRepository.save(report);
        log.info("Reporte almacenado con éxito.");
    }

    @Override
    public ResponseEntity<?> getReportById(String id) {
        log.info("Obteniendo reporte con id {}", id);
        try {
            Report report = reportRepository.findById(ObjectIdMapperUtil.map(id)).orElseThrow(() -> new  NotFoundException("No se encontró un reporte con id : ".concat(String.valueOf(id))));
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseDto(200, "Reporte obtenido con éxito.", reportMapper.toDTO(report)));

        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseDto(404, e.getMessage(), null));

        }
 }

    @Override
    public ResponseEntity<?> deleteReportById(String id) {

        try {
            Report existingReport =  reportRepository.findById(ObjectIdMapperUtil.map(id)).orElseThrow(() -> new  NotFoundException("No se encontró un reporte con id : ".concat(id)));
            existingReport.setStatus(EnumStatusReport.ELIMINADO);
            saveReport(existingReport);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseDto(200, "Reporte eliminado con éxito", null));
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseDto(404, e.getMessage(), null));
        }

    }

    @Override
    public ResponseEntity<?> getAllReportsByStatus(EnumStatusReport status) {
        List<Report> listResult = reportRepository.findByStatus(status.name());
        List<GeneralReportDTO> dataFiltered = reportMapper.toListDTO(listResult);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDto(200, "Datos obtenidos con éxito", dataFiltered));

    }

    @Override
    public ResponseEntity<?> markAsImportant(String reportId, boolean isImportant) throws NotFoundException {

        Report report = reportRepository.findById(ObjectIdMapperUtil.map(reportId)).orElseThrow(() -> new  NotFoundException("No se encontró un reporte con id : ".concat(String.valueOf(reportId))));
        report.setCounterImportant(isImportant ? report.getCounterImportant() + 1 : report.getCounterImportant() -1 );
        saveReport(report);
        ResponseDto responseDto = new ResponseDto(HttpStatus.OK.value(), "El reporte se actualizó con éxito", reportMapper.toDTO(report));
        return ResponseEntity.status(responseDto.getCodigo()).body(responseDto);
    }


    private boolean validateChangeStatus(String newStatus) {
        boolean flagAdmin = OperationUtils.validateUserByRol(EnumUserType.ADMINISTRADOR.name());

        // no es admin solo puede resuelto
        if (!flagAdmin && !EnumStatusReport.RESUELTO.name().equals(newStatus)) {
            return false;
        } else {
            return true;
        }
    }
}
