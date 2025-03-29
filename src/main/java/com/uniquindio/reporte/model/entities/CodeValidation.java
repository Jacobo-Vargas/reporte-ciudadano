package com.uniquindio.reporte.model.entities;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CodeValidation {

    private LocalDate  date;

    private  String code;
}
