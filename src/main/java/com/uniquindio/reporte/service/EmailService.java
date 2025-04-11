package com.uniquindio.reporte.service;

public interface EmailService {


    void enviarConfirmacionCreacionReporte(String destinatario, String tituloReporte);
    void enviarNotificacionComentario(String destinatario, String tituloReporte, String contenidoComentario, String autorComentario);

}
