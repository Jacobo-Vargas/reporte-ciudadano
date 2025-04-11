package com.uniquindio.reporte.utils;

public class EmailRequest {
    private String para;
    private String mensaje;
    private String ruta; // Solo necesario para el caso de archivo adjunto

    // Getters y setters
    public String getPara() {
        return para;
    }

    public void setPara(String para) {
        this.para = para;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }
}
