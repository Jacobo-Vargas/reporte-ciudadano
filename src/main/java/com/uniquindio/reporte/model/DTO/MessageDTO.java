package com.uniquindio.reporte.model.DTO;

public record MessageDTO<T>(boolean error, T message) {

}
