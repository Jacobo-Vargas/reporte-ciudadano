package com.uniquindio.reporte.service;


import com.uniquindio.reporte.model.DTO.comment.UpdateCommentDTO;
import jakarta.mail.MessagingException;
import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;

public interface EmailService {

    ResponseEntity<?> enviarTextoPlano(String destinatario, String asunto, String mensaje);
    ResponseEntity<?> enviarHtml(String destinatario, String asunto, String contenidoHtml) throws MessagingException;
    ResponseEntity<?> enviarConAdjunto(String destinatario, String asunto, String texto, String rutaArchivo) throws MessagingException;

}
