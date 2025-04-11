package com.uniquindio.reporte.service.impl;

import com.uniquindio.reporte.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    // 🔹 Enviar texto simple
    @Override
    public ResponseEntity<?> enviarTextoPlano(String destinatario, String asunto, String mensaje) {
        SimpleMailMessage correo = new SimpleMailMessage();
        correo.setTo(destinatario);
        correo.setSubject(asunto);
        correo.setText(mensaje);
        mailSender.send(correo);
        return null;
    }

    // 🔹 Enviar HTML
    @Override
    public ResponseEntity<?> enviarHtml(String destinatario, String asunto, String contenidoHtml) throws MessagingException {
        MimeMessage mensaje = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mensaje, true, "UTF-8");
        helper.setTo(destinatario);
        helper.setSubject(asunto);
        helper.setText(contenidoHtml, true); // true = HTML
        mailSender.send(mensaje);
        return null;
    }

    // 🔹 Enviar con archivo adjunto
    @Override
    public ResponseEntity<?> enviarConAdjunto(String destinatario, String asunto, String texto, String rutaArchivo) throws MessagingException {
        MimeMessage mensaje = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mensaje, true);
        helper.setTo(destinatario);
        helper.setSubject(asunto);
        helper.setText(texto);

        FileSystemResource archivo = new FileSystemResource(new File(rutaArchivo));
        helper.addAttachment(archivo.getFilename(), archivo);

        mailSender.send(mensaje);
        return null;
    }
}