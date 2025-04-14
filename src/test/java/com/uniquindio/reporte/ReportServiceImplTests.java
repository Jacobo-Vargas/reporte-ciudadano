package com.uniquindio.reporte;

import com.uniquindio.reporte.exceptions.NotFoundException;
import com.uniquindio.reporte.mapper.ReportMapper;
import com.uniquindio.reporte.model.DTO.location.LocationDTO;
import com.uniquindio.reporte.model.DTO.report.CreateReportDTO;
import com.uniquindio.reporte.model.DTO.report.GeneralReportDTO;
import com.uniquindio.reporte.model.DTO.report.UpdateReportDTO;
import com.uniquindio.reporte.model.entities.HistoryReport;
import com.uniquindio.reporte.model.entities.Location;
import com.uniquindio.reporte.model.entities.Report;
import com.uniquindio.reporte.model.entities.User;
import com.uniquindio.reporte.model.enums.reports.EnumStatusReport;
import com.uniquindio.reporte.repository.ReportRepository;
import com.uniquindio.reporte.repository.UserRepository;
import com.uniquindio.reporte.service.EmailService;
import com.uniquindio.reporte.service.HistoryReportService;
import com.uniquindio.reporte.service.impl.LocationServiceImpl;
import com.uniquindio.reporte.service.impl.ReportServiceImpl;
import com.uniquindio.reporte.service.impl.UploadImageService;
import com.uniquindio.reporte.utils.ObjectIdMapperUtil;
import com.uniquindio.reporte.utils.ResponseDto;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.OK;

class ReportServiceImplTest {

    @InjectMocks
    private ReportServiceImpl reportService;

    @Mock
    private ReportRepository reportRepository;
    @Mock
    private ReportMapper reportMapper;
    @Mock
    private LocationServiceImpl locationService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private EmailService emailService;
    @Mock
    private HistoryReportService historyReportService;
    @Mock
    private UploadImageService uploadImageService;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        var auth = new UsernamePasswordAuthenticationToken(
                "jacobovargas493@gmail.com",
                null,
                List.of(new SimpleGrantedAuthority("ROLE_CLIENTE"))
        );
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    // getReportById
    @Test
    void getReportById_success() {
        String id = "67f2357558e86a0f559b8e11";
        ObjectId objectId = new ObjectId(id);
        Report report = new Report();
        report.setId(objectId);

        GeneralReportDTO dto = new GeneralReportDTO(id, "desc", "title", "userId", "catId", "PENDIENTE");

        when(reportRepository.findById(objectId)).thenReturn(Optional.of(report));
        when(reportMapper.toDTO(report)).thenReturn(dto);

        ResponseDto body = (ResponseDto) reportService.getReportById(id).getBody();

        assertEquals(200, body.getCodigo());
        assertEquals(dto, body.getDatos());
    }

    @Test
    void getReportById_notFound() {
        String id = "67f2357558e86a0f559b8e15";
        when(reportRepository.findById(new ObjectId(id))).thenReturn(Optional.empty());

        ResponseDto body = (ResponseDto) reportService.getReportById(id).getBody();

        assertEquals(404, body.getCodigo());
        assertNull(body.getDatos());
    }

    // createReport
    @Test
    void createReport_success() throws IOException {
        CreateReportDTO dto = new CreateReportDTO("desc", "userId", "title", "catId",
                new LocationDTO(1.0f, 2.0f, "place", "desc"));

        MultipartFile photo = mock(MultipartFile.class);
        List<MultipartFile> photos = List.of(photo);

        Report report = new Report();
        report.setId(new ObjectId());
        report.setUserId(ObjectIdMapperUtil.map("67f2357558e86a0f559b8e11"));

        when(reportMapper.toEntity(dto)).thenReturn(report);
        when(locationService.saveLocation(dto.location())).thenReturn(new Location());
        when(historyReportService.save(any())).thenReturn(new HistoryReport());
        when(uploadImageService.uploadFile(any())).thenReturn("http://img.com");
        when(userRepository.findById("userId")).thenReturn(Optional.of(new User()));
        when(reportMapper.toDTO(report)).thenReturn(new GeneralReportDTO(report.getId().toString(), "desc", "title", "userId", "catId", "PENDIENTE"));

        ResponseDto body = (ResponseDto) reportService.createReport(dto, photos).getBody();

        assertEquals(200, body.getCodigo());
        assertEquals("Reporte guardado con éxito", body.getMensaje());
    }

    // updateReport
    @Test
    void updateReport_success() {
        String id = new ObjectId().toString();
        UpdateReportDTO dto = new UpdateReportDTO("PENDIENTE", "titulo",
                new LocationDTO(1, 1, "loc", "desc"), id, "nueva descripcion");

        Report report = new Report();
        report.setId(new ObjectId(id));

        when(reportRepository.findById(any())).thenReturn(Optional.of(report));
        when(locationService.saveLocation(dto.location())).thenReturn(new Location());
        when(reportMapper.toDTO(report)).thenReturn(new GeneralReportDTO(id, "nueva descripcion", "titulo", "userId", "catId", "PENDIENTE"));

        ResponseDto body = (ResponseDto) reportService.updateReport(dto).getBody();

        assertEquals(200, body.getCodigo());
        assertEquals("Reporte actualizado con éxito", body.getMensaje());
    }

    @Test
    void updateReport_notFound() {
        UpdateReportDTO dto = new UpdateReportDTO("PENDIENTE", "title",
                new LocationDTO(1, 1, "loc", "desc"), "67f2357558e86a0f559b8e17", "descripcion");

        when(reportRepository.findById(any())).thenReturn(Optional.empty());

        ResponseDto body = (ResponseDto) reportService.updateReport(dto).getBody();

        assertEquals(404, body.getCodigo());
    }

    // deleteReportById
    @Test
    void deleteReportById_success() {
        String id = new ObjectId().toString();
        Report report = new Report();
        report.setId(new ObjectId(id));

        when(reportRepository.findById(any())).thenReturn(Optional.of(report));

        ResponseDto body = (ResponseDto) reportService.deleteReportById(id).getBody();

        assertEquals(200, body.getCodigo());
        assertEquals("Reporte eliminado con éxito", body.getMensaje());
    }

    @Test
    void deleteReportById_notFound() {
        when(reportRepository.findById(any())).thenReturn(Optional.empty());

        ResponseDto body = (ResponseDto) reportService.deleteReportById("67f2357558e86a0f559b8e17").getBody();

        assertEquals(404, body.getCodigo());
    }

    // getReport
    @Test
    void getReport_success() {
        Report r = new Report();
        when(reportRepository.findAll()).thenReturn(List.of(r));
        when(reportMapper.toListDTO(any())).thenReturn(List.of(
                new GeneralReportDTO("id", "desc", "title", "user", "cat", "PENDIENTE")
        ));

        ResponseEntity<?> response = reportService.getReport();
        assertEquals(OK, response.getStatusCode());
    }

    // getAllReportsByStatus
    @Test
    void getAllReportsByStatus_success() {
        when(reportRepository.findByStatus(EnumStatusReport.PENDIENTE.name())).thenReturn(List.of(new Report()));
        when(reportMapper.toListDTO(any())).thenReturn(List.of(
                new GeneralReportDTO("id", "desc", "title", "user", "cat", "PENDIENTE")
        ));

        ResponseDto body = (ResponseDto) reportService.getAllReportsByStatus(EnumStatusReport.PENDIENTE).getBody();

        assertEquals(200, body.getCodigo());
        assertEquals("Datos obtenidos con éxito", body.getMensaje());
    }

    // markAsImportant
    @Test
    void markAsImportant_success() throws NotFoundException {
        String id = new ObjectId().toString();
        Report report = new Report();
        report.setId(new ObjectId(id));
        report.setCounterImportant(0);

        when(reportRepository.findById(any())).thenReturn(Optional.of(report));
        when(reportMapper.toDTO(any())).thenReturn(new GeneralReportDTO(id, "desc", "title", "user", "cat", "PENDIENTE"));

        ResponseDto body = (ResponseDto) reportService.markAsImportant(id, true).getBody();

        assertEquals(200, body.getCodigo());
    }

    @Test
    void markAsImportant_notFound() {
        when(reportRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> reportService.markAsImportant("67f2357558e86a0f559b8e17", true));
    }
}