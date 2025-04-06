package com.uniquindio.reporte.service.impl;

import com.uniquindio.reporte.mapper.ReportMapper;
import com.uniquindio.reporte.model.DTO.report.ChangeStatusReportDTO;
import com.uniquindio.reporte.model.DTO.report.CreateReportDTO;
import com.uniquindio.reporte.model.DTO.report.GeneralReportDTO;
import com.uniquindio.reporte.model.DTO.report.UpdateReportDTO;
import com.uniquindio.reporte.model.DTO.user.ResponseUserDto;
import com.uniquindio.reporte.model.entities.Report;
import com.uniquindio.reporte.model.entities.User;
import com.uniquindio.reporte.model.enums.reports.EnumStateReport;
import com.uniquindio.reporte.model.enums.users.EnumUserStatus;
import com.uniquindio.reporte.model.enums.users.EnumUserType;
import com.uniquindio.reporte.repository.ReportRepository;
import com.uniquindio.reporte.repository.UserRepository;
import com.uniquindio.reporte.service.ReportService;
import com.uniquindio.reporte.utils.ResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private  final UserRepository userRepository;

    private final ReportRepository reportRepository;
    private final ReportMapper reportMapper;


    @Override
    public ResponseEntity<?> saveReport(Report report) {
        return null;
    }

    @Override
    public ResponseEntity<?> createReport(CreateReportDTO createReportDTO) {
        return null;
    }

    @Override
    public ResponseEntity<?> updateReport(UpdateReportDTO updateReportDTO) {
        return null;
    }




    @Override
    public ResponseEntity<?> changeStatusReport(ChangeStatusReportDTO changeStatusReportDTO) {
        return null;
    }

    @Override
    public ResponseEntity<?> getReport() {
        List<Report> listR = reportRepository.findAll();
        List<GeneralReportDTO> list = reportMapper.toListDTO(listR);
        return ResponseEntity.ok(list);
    }



    @Override
    public ResponseEntity<?> getReportById(ObjectId id) {
        return null;
    }

    @Override
    public ResponseEntity<?> deleteReportById(ObjectId id) {
        return null;
    }

    @Override
    public ResponseEntity<?> getAllReportsByStatus(boolean isActive) {
        return null;
    }


    //cambio estado admin
    @Override
    public ResponseEntity<?> changeUserStatusReportAdmin(String id, String estado, String idAdmin) throws Exception {
        ObjectId objectId = new ObjectId(id);
        ObjectId objectIdAdmin=new ObjectId(idAdmin);

        User admin = userRepository.findById(objectIdAdmin)
                .orElseThrow(() -> new Exception("Usuario no encontrado"));

        if(admin.getUserType().equals(EnumUserType.ADMINISTRADOR)) {

            Optional<Report> optionalReport = reportRepository.findById(objectId);
            Report report = reportRepository.findById(objectId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "El reporte no ha sido encontrado"));
            try {
                EnumStateReport nuevoEstado = EnumStateReport.valueOf(estado.toUpperCase());
                report.setState(nuevoEstado);
                GeneralReportDTO reportDto = reportMapper.toDTO(reportRepository.save(report));
                return ResponseEntity.ok(new ResponseDto(200, "Estado actualizado exitosamente", reportDto));
            } catch (IllegalArgumentException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ResponseDto(400, "Estado inválido: " + estado, null));
            }
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseDto(400, "El estado del reporte no ha podido cambiar. " +
                            "El id del usuario no hace referencia al de un  Administrador", null));
        }
    }


}
