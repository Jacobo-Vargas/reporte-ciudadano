package com.uniquindio.reporte;

import com.uniquindio.reporte.mapper.CommentMapper;
import com.uniquindio.reporte.model.DTO.comment.CreateCommentDTO;
import com.uniquindio.reporte.model.DTO.comment.UpdateCommentDTO;
import com.uniquindio.reporte.model.entities.Comment;
import com.uniquindio.reporte.model.entities.Report;
import com.uniquindio.reporte.model.entities.User;
import com.uniquindio.reporte.model.enums.EnumStatusComment;
import com.uniquindio.reporte.repository.CommentRepository;
import com.uniquindio.reporte.repository.ReportRepository;
import com.uniquindio.reporte.repository.UserRepository;
import com.uniquindio.reporte.service.impl.CommentServiceImpl;
import com.uniquindio.reporte.service.EmailService;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

    @InjectMocks
    private CommentServiceImpl commentService;

    @Mock
    private CommentMapper commentMapper;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private ReportRepository reportRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private EmailService emailService;

    private CreateCommentDTO createCommentDTO;
    private UpdateCommentDTO updateCommentDTO;
    private Comment comment;
    private Report report;
    private User user;

    @BeforeEach
    void setUp() {
        ObjectId userId = new ObjectId("507f1f77bcf86cd799439011");
        ObjectId reportId = new ObjectId("507f1f77bcf86cd799439012");

        createCommentDTO = new CreateCommentDTO("Mensaje de prueba", userId.toHexString(), reportId.toHexString());
        updateCommentDTO = new UpdateCommentDTO("Mensaje actualizado");

        comment = new Comment();
        comment.setId(new ObjectId());
        comment.setMessage("Mensaje de prueba");
        comment.setUserId(userId.toHexString());
        comment.setReportId(reportId.toHexString());
        comment.setStatus(EnumStatusComment.ACTIVO);

        report = new Report();
        report.setId(reportId);
        report.setUserId(userId);

        user = new User();
        user.setId(userId);
        user.setName("Usuario Ejemplo");
        user.setEmail("correo@ejemplo.com");
    }

    @Test
    void createComment_shouldReturnOk() {
        when(commentMapper.toDocumentCreate(any())).thenReturn(comment);
        when(reportRepository.findById(any())).thenReturn(Optional.of(report));
        when(userRepository.findById(eq(new ObjectId(comment.getUserId())))).thenReturn(Optional.of(user));
        when(commentRepository.save(any())).thenReturn(comment);

        ResponseEntity<?> response = commentService.createComment(createCommentDTO);

        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void updateComment_shouldReturnOk() {
        when(commentRepository.findById(any())).thenReturn(Optional.of(comment));
        when(reportRepository.findById(any())).thenReturn(Optional.of(report));

        ResponseEntity<?> response = commentService.updateComment(comment.getId(), updateCommentDTO);

        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void deleteComment_shouldReturnOk() {
        when(commentRepository.findById(any())).thenReturn(Optional.of(comment));

        ResponseEntity<?> response = commentService.deleteComment(comment.getId());

        assertEquals(200, response.getStatusCodeValue());
    }


    @Test
    void getComments_shouldReturnOk() {
        when(commentRepository.findAll()).thenReturn(java.util.List.of(comment));

        ResponseEntity<?> response = commentService.getComments();

        assertEquals(200, response.getStatusCodeValue());
    }
}