package com.uniquindio.reporte.controllers;

import com.uniquindio.reporte.model.DTO.comment.CreateCommentDTO;
import com.uniquindio.reporte.model.DTO.comment.UpdateCommentDTO;
import com.uniquindio.reporte.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    //crear comentario
    @PostMapping()
    public ResponseEntity<?> createComment(@RequestBody @Valid CreateCommentDTO createCommentDTO) {
        return commentService.createComment(createCommentDTO);
    }

    //actualizar comentario
    @PutMapping("/{id}")
    public ResponseEntity<?> updateComment(@PathVariable ObjectId id, @RequestBody @Valid UpdateCommentDTO updateCommentDTO) {
        return commentService.updateComment(id, updateCommentDTO);
    }

    //eliminar comentario
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable String id) {
        try {
            ObjectId objectId = new ObjectId(id);
            return commentService.deleteComment(objectId); // 🔁 ahora usa ObjectId
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("ID de comentario inválido");
        }
    }

    //obtener comentario por id
    @GetMapping("/{id}")
    public ResponseEntity<?> getComment(@PathVariable ObjectId id) {
        return commentService.getComment(id);
    }



    // obtener todos los comentarios
    @GetMapping()
    public ResponseEntity<?> getComments() {
        return commentService.getComments();
    }

}
