package com.uniquindio.reporte.model.DTO.loggin;



public class MensajeDto<T> {

    private boolean estado;
    private T mensaje;

    public MensajeDto(boolean estado, T mensaje) {
        this.estado = estado;
        this.mensaje = mensaje;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public T getMensaje() {
        return mensaje;
    }

    public void setMensaje(T mensaje) {
        this.mensaje = mensaje;
    }
}
