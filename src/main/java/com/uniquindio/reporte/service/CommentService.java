package com.uniquindio.reporte.service;

import com.uniquindio.reporte.model.DTO.comment.CreateCommentDTO;
import com.uniquindio.reporte.model.DTO.comment.UpdateCommentDTO;
import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;

public interface CommentService {

    ResponseEntity<?> createComment(CreateCommentDTO createCommentDTO);
    ResponseEntity<?> updateComment(ObjectId id,UpdateCommentDTO updateCommentDTO);
    ResponseEntity<?> deleteComment(ObjectId id);
    ResponseEntity<?> getComment(ObjectId id);
    ResponseEntity<?> getComments();
    ResponseEntity<?> getCommentsByStatus(String status);

}
