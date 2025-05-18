package com.uniquindio.reporte.model.entities;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Document(collection = "code_validation")
public class CodeValidation {

    private String EmailUser;

    private LocalDateTime expiresAt;

    private  String code;
}
