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


    @Override
    public void enviarConfirmacionCreacionReporte(String destinatario, String tituloReporte) {
        String asunto = "Tu reporte ha sido creado con éxito";
        String cuerpo = "¡Gracias por usar nuestra plataforma!\n\n" +
                "Hemos recibido tu reporte titulado: \"" + tituloReporte + "\".\n" +
                "Nuestro equipo lo revisará lo más pronto posible.\n\n" +
                "Puedes hacer seguimiento desde tu perfil.";

        SimpleMailMessage correo = new SimpleMailMessage();
        correo.setTo(destinatario);
        correo.setSubject(asunto);
        correo.setText(cuerpo);
        mailSender.send(correo);
    }

    @Override
    public void enviarNotificacionComentario(String destinatario, String tituloReporte, String contenidoComentario, String autorComentario) {
        String asunto = "Nuevo comentario en tu reporte";
        String cuerpo = "¡Hola!\n\n" +
                "El usuario \"" + autorComentario + "\" ha comentado en tu reporte titulado: \"" + tituloReporte + "\"\n\n" +
                "Comentario: \"" + contenidoComentario + "\"\n\n" +
                "Ingresa a la plataforma para ver más detalles.";

        SimpleMailMessage correo = new SimpleMailMessage();
        correo.setTo(destinatario);
        correo.setSubject(asunto);
        correo.setText(cuerpo);
        mailSender.send(correo);
    }

    @Override
    public void sendCodeVerifaction(String recipient, String code) throws Exception {
        String subject = "Código de verificación para registro";
        String body = """
            ¡Hola!

            Gracias por registrarte en nuestra plataforma.

            Para completar tu registro, por favor ingresa el siguiente código de verificación:

            👉 CÓDIGO: %s

            Este código expirará en 1 minuto por motivos de seguridad.

            Si tú no solicitaste este código, puedes ignorar este mensaje.

            Saludos,
            El equipo de soporte.
            """.formatted(code);

        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipient);
        email.setSubject(subject);
        email.setText(body);
        mailSender.send(email);
    }

}