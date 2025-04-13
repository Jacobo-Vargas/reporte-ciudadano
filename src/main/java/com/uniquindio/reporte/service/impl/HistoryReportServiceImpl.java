package com.uniquindio.reporte.service.impl;

import com.uniquindio.reporte.exceptions.NotFoundException;
import com.uniquindio.reporte.mapper.HistoryReportMapper;
import com.uniquindio.reporte.mapper.ReportMapper;
import com.uniquindio.reporte.model.DTO.historyReport.CreateHistoryReportDTO;
import com.uniquindio.reporte.model.DTO.historyReport.UpdateHistoryReportDTO;
import com.uniquindio.reporte.model.entities.HistoryReport;
import com.uniquindio.reporte.repository.HistoryReportRepository;
import com.uniquindio.reporte.service.HistoryReportService;
import com.uniquindio.reporte.utils.ObjectIdMapperUtil;
import com.uniquindio.reporte.utils.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HistoryReportServiceImpl implements HistoryReportService {

    private final HistoryReportRepository historyReportRepository;
    private final HistoryReportMapper historyReportMapper;
    private final ReportMapper reportMapper;


    @Override
    public ResponseEntity<?> createHistoryReport(CreateHistoryReportDTO dto) {
        HistoryReport entity = historyReportMapper.toEntity(dto);
        historyReportRepository.save(entity);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDto(201, "Historial creado con éxito", historyReportMapper.toDTO(entity)));
    }

    @Override
    public ResponseEntity<?> updateHistoryReport(UpdateHistoryReportDTO dto) {
        try {
            ObjectId id = ObjectIdMapperUtil.map(dto.id());
            HistoryReport report = historyReportRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("No se encontró el historial con ese id"));

            report.setObservations(dto.observations());
            report.setClienteId(ObjectIdMapperUtil.map(dto.clienteId()));
            report.setEnumStatusReport(dto.enumStatusReport());
            report.setReportId(ObjectIdMapperUtil.map(dto.reportId()));


            historyReportRepository.save(report);

            return ResponseEntity.ok(new ResponseDto(200, "Historial actualizado con éxito", historyReportMapper.toDTO(report)));
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseDto(404, e.getMessage(), null));
        }
    }

    @Override
    public ResponseEntity<?> getAllHistoryReports() {
        List<HistoryReport> list = historyReportRepository.findAll();
        return ResponseEntity.ok(new ResponseDto(200, "Historiales obtenidos", historyReportMapper.toListDTO(list)));
    }

    @Override
    public ResponseEntity<?> getHistoryReportById(String id) {
        try {
            HistoryReport report = historyReportRepository.findById(ObjectIdMapperUtil.map(id))
                    .orElseThrow(() -> new NotFoundException("No se encontró el historial con id: " + id));
            return ResponseEntity.ok(new ResponseDto(200, "Historial obtenido", historyReportMapper.toDTO(report)));
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseDto(404, e.getMessage(), null));
        }
    }

    @Override
    public ResponseEntity<?> deleteHistoryReport(String id) {
        try {
            HistoryReport report = historyReportRepository.findById(ObjectIdMapperUtil.map(id))
                    .orElseThrow(() -> new NotFoundException("No se encontró el historial con id: " + id));
            historyReportRepository.delete(report);
            return ResponseEntity.ok(new ResponseDto(200, "Historial eliminado", null));
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseDto(404, e.getMessage(), null));
        }
    }

    public HistoryReport save(CreateHistoryReportDTO dto) {
        HistoryReport entity = historyReportMapper.toEntity(dto);
        return historyReportRepository.save(entity);
    }
}
