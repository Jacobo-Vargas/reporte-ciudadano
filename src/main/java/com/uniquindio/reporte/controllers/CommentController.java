package com.uniquindio.reporte.controllers;

import com.uniquindio.reporte.model.DTO.CreateCategoryDTO;
import com.uniquindio.reporte.model.DTO.CreateCommentDTO;
import com.uniquindio.reporte.model.DTO.UpdateCategoryDTO;
import com.uniquindio.reporte.model.DTO.UpdateCommentDTO;
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
    @PutMapping("/{Id}")
    public ResponseEntity<?> updateComment(@PathVariable ObjectId Id, @RequestBody @Valid UpdateCommentDTO updateCommentDTO) {
        return commentService.updateComment(Id, updateCommentDTO);
    }

    //eliminar comentario
    @DeleteMapping("/{Id}")
    public ResponseEntity<?> deleteComment(@PathVariable ObjectId Id) {
        return commentService.deleteComment(Id);
    }

    //obtener comentario por id
    @GetMapping("/{Id}")
    public ResponseEntity<?> getComment(@PathVariable ObjectId Id) {
        return commentService.getComment(Id);
    }



    // obtener todos los comentarios
    @GetMapping()
    public ResponseEntity<?> getComments() {
        return commentService.getComments();
    }


}
