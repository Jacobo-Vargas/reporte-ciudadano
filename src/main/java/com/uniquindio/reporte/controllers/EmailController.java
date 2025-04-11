package com.uniquindio.reporte.controllers;

import com.uniquindio.reporte.service.EmailService;
import com.uniquindio.reporte.utils.EmailRequest;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/correo")
public class EmailController {

    @Autowired
    private EmailService emailService;

    // Enviar Correo Simple
    @PostMapping("/simple")
    public ResponseEntity<String> enviarSimple(@RequestBody EmailRequest emailRequest) {
        emailService.enviarTextoPlano(emailRequest.getPara(), "Asunto de prueba", emailRequest.getMensaje());
        return ResponseEntity.ok("Correo enviado.");
    }

    // Enviar Correo HTML
    @PostMapping("/html")
    public ResponseEntity<String> enviarHtml(@RequestBody EmailRequest emailRequest) throws MessagingException {
        String html = "<h1>Comentario recibido</h1><p>Gracias por tu reporte.</p>";
        emailService.enviarHtml(emailRequest.getPara(), "Comentario", html);
        return ResponseEntity.ok("Correo HTML enviado.");
    }

    // Enviar Correo con Archivo Adjunto
    @PostMapping("/adjunto")
    public ResponseEntity<String> enviarConAdjunto(@RequestBody EmailRequest emailRequest) throws MessagingException {
        emailService.enviarConAdjunto(emailRequest.getPara(), "Documento adjunto", "Revisa el archivo adjunto.", emailRequest.getRuta());
        return ResponseEntity.ok("Correo con adjunto enviado.");
    }
}

