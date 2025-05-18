package com.uniquindio.reporte.service.impl;

import com.uniquindio.reporte.mapper.CommentMapper;
import com.uniquindio.reporte.model.DTO.category.GeneralCategoryDTO;
import com.uniquindio.reporte.model.DTO.comment.CreateCommentDTO;
import com.uniquindio.reporte.model.DTO.comment.UpdateCommentDTO;
import com.uniquindio.reporte.model.entities.Category;
import com.uniquindio.reporte.model.entities.Comment;
import com.uniquindio.reporte.model.entities.Report;
import com.uniquindio.reporte.model.entities.User;
import com.uniquindio.reporte.model.enums.EnumStatusComment;
import com.uniquindio.reporte.model.enums.reports.EnumStatusReport;
import com.uniquindio.reporte.repository.CommentRepository;
import com.uniquindio.reporte.repository.ReportRepository;
import com.uniquindio.reporte.repository.UserRepository;
import com.uniquindio.reporte.service.CommentService;
import com.uniquindio.reporte.service.EmailService;
import com.uniquindio.reporte.utils.ObjectIdMapperUtil;
import com.uniquindio.reporte.utils.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentMapper commentMapper;
    private final CommentRepository commentRepository;
    private final ReportRepository reportRepository;
    private final UserRepository userRepository;
    private final EmailService emailService;



    @Override
    public ResponseEntity<?> createComment(CreateCommentDTO createCommentDTO) {
        try {
            ObjectId reportObjectId = ObjectIdMapperUtil.map(createCommentDTO.reportId());
            Report reporte = reportRepository.findById(reportObjectId).orElse(null);

            if (reporte == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ResponseDto(HttpStatus.NOT_FOUND.value(), "El reporte no existe", null));
            }

            Comment comment = commentMapper.toDocumentCreate(createCommentDTO);
            commentRepository.save(comment);

            User creador = userRepository.findById(reporte.getUserId()).orElse(null);
            User autorComentario = (User) userRepository.findById(comment.getUserId()).orElse(null);

            if (creador != null && autorComentario != null) {
                emailService.enviarNotificacionComentario(
                        creador.getEmail(),
                        reporte.getTitle(),
                        comment.getMessage(),
                        autorComentario.getName()
                );
            }

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ResponseDto(HttpStatus.CREATED.value(), "Comentario creado exitosamente", null));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseDto(HttpStatus.BAD_REQUEST.value(), "Error interno: " + e.getMessage(), null));
        }
    }


    @Override
    public ResponseEntity<?> updateComment(ObjectId Id, UpdateCommentDTO updateCommentDTO) {
        Optional<Comment> optionalComment = commentRepository.findById(Id);
        if (optionalComment.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseDto(404, "Comentario no encontrado", null));
        }

        Comment comment = optionalComment.get();

        if (comment.getStatus() == EnumStatusComment.ELIMINADO) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseDto(HttpStatus.BAD_REQUEST.value(), "Este comentario fue eliminado", null));
        }

        Report report = reportRepository.findById(ObjectIdMapperUtil.map(comment.getReportId())).orElse(null);
        if (report == null || report.getStatus() == EnumStatusReport.ELIMINADO) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseDto(400, "El reporte asociado fue eliminado", null));
        }

        if (updateCommentDTO.message() != null) comment.setMessage(updateCommentDTO.message());

        try {
            commentRepository.save(comment);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto(HttpStatus.BAD_REQUEST.value(), "error interno", null));
        }

        return ResponseEntity.ok(new ResponseDto(200, "Comentario actualizado correctamente", comment));
    }

    @Override
    public ResponseEntity<?> deleteComment(ObjectId Id) {
        Optional<Comment> optionalComment = commentRepository.findById(Id);
        if (optionalComment.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseDto(HttpStatus.NOT_FOUND.value(), "Comentario no encontrado", null));
        }

        try {
            Comment comment = optionalComment.get();
            comment.setStatus(EnumStatusComment.ELIMINADO);
            commentRepository.save(comment);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto(HttpStatus.BAD_REQUEST.value(), "error interno", null));
        }

        return ResponseEntity.ok(new ResponseDto(HttpStatus.OK.value(), "Comentario eliminado lógicamente", null));
    }

    @Override
    public ResponseEntity<?> getComment(ObjectId id) {
        Optional<Comment> optionalComment = commentRepository.findById(id);

        if (optionalComment.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseDto(404, "Comentario no encontrado", null));
        }

        Comment comment = optionalComment.get();

        if (comment.getStatus() == EnumStatusComment.ELIMINADO) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseDto(400, "Este comentario fue eliminado", null));
        }

        Report report = reportRepository.findById(ObjectIdMapperUtil.map(comment.getReportId())).orElse(null);
        if (report == null || report.getStatus() == EnumStatusReport.ELIMINADO) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseDto(400, "El reporte asociado fue eliminado", null));
        }

        return ResponseEntity.ok(new ResponseDto(200, "Comentario obtenido correctamente", comment));
    }

    @Override
    public ResponseEntity<?> getComments() {
        List<Comment> comments = commentRepository.findAll()
                .stream()
                .filter(c -> c.getStatus() == EnumStatusComment.ACTIVO)
                .filter(c -> {
                    Report report = reportRepository.findById(ObjectIdMapperUtil.map(c.getReportId())).orElse(null);
                    return report != null && report.getStatus() != EnumStatusReport.ELIMINADO;
                })
                .toList();

        if (comments.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(200, "No hay comentarios activos", List.of()));
        }

        return ResponseEntity.ok(new ResponseDto(HttpStatus.OK.value(), "Lista de comentarios activos obtenida", comments));
    }

    @Override
    public ResponseEntity<?> getCommentsByStatus(String status) {
        List<Comment> comments = commentRepository.findAll()
                .stream()
                .filter(c -> c.getStatus().name().equals(status))
                .toList();

        if (comments.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        List<GeneralCategoryDTO> categoriesR = commentMapper.toListDTO(comments);
        return ResponseEntity.ok(new ResponseDto(HttpStatus.OK.value(), "Lista de categorías obtenida", categoriesR));

    }
}
