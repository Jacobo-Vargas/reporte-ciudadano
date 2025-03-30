package com.uniquindio.reporte.model.DTO.utilsDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ResponseDto {

    private int codigo;

    private String mensaje;

    private Object datos;

}
