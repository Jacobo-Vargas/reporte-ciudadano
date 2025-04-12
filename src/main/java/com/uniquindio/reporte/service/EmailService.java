package com.uniquindio.reporte.service;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public interface EmailService {


    void enviarConfirmacionCreacionReporte(String destinatario, String tituloReporte);
    void enviarNotificacionComentario(String destinatario, String tituloReporte, String contenidoComentario, String autorComentario);
    void sendCodeVerifaction( String email, String code) throws Exception;
}
