package com.uniquindio.reporte.service.impl;

import com.uniquindio.reporte.mapper.CommentMapper;
import com.uniquindio.reporte.model.DTO.comment.CreateCommentDTO;
import com.uniquindio.reporte.model.DTO.comment.UpdateCommentDTO;
import com.uniquindio.reporte.model.entities.Comment;
import com.uniquindio.reporte.repository.CommentRepository;
import com.uniquindio.reporte.service.CommentService;
import com.uniquindio.reporte.service.EmailService;
import com.uniquindio.reporte.utils.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
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



    @Override
    public ResponseEntity<?> createComment(CreateCommentDTO createCommentDTO) {

        Comment comment = commentMapper.toDocumentCreate(createCommentDTO);

        try {

            commentRepository.save(comment);

        }catch (Exception e){

            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto(HttpStatus.BAD_REQUEST.value(),"error interno",null));
        }
        return ResponseEntity.status(HttpStatus.OK).body("Comentario creado exitosamente");

    }



    @Override
    public ResponseEntity<?> updateComment(ObjectId Id, UpdateCommentDTO updateCommentDTO) {
        Optional<Comment> optionalComment = commentRepository.findById(Id);
        if (optionalComment.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseDto(404, "Comentario no encontrado", null));
        }

        Comment comment = optionalComment.get();
        if (updateCommentDTO.message() != null) comment.setMessage(updateCommentDTO.message());

        try{
            commentRepository.save(comment);

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto(HttpStatus.BAD_REQUEST.value(),"error interno",null));

        }

        return ResponseEntity.ok(new ResponseDto(200, "Comentario actualizado correctamente", comment));

    }

    @Override
    public ResponseEntity<?> deleteComment(ObjectId Id) {
        if (!commentRepository.existsById(Id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseDto(HttpStatus.NOT_FOUND.value(), "Comentario no encontrado", null));
        }

        try {
            commentRepository.deleteById(Id);
        }catch (Exception e){
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto(HttpStatus.BAD_REQUEST.value(), "error interno",null));
        }

        return ResponseEntity.ok(new ResponseDto(HttpStatus.OK.value(), "Comentario eliminado correctamente", null));
    }

    @Override
    public ResponseEntity<?> getComment(ObjectId Id) {
        Optional<Comment> comment = commentRepository.findById(Id);
        return comment.map(value -> ResponseEntity.ok(new ResponseDto(HttpStatus.OK.value(), "Comentario encontrado", value)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ResponseDto(404, "Comentario no encontrado", null)));
    }

    @Override
    public ResponseEntity<?> getComments() {
        List<Comment> comments = commentRepository.findAll();
        if (comments.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).build();
        }

        return ResponseEntity.ok(new ResponseDto(HttpStatus.OK.value(), "Lista de comentarios obtenida", comments));

    }
}
