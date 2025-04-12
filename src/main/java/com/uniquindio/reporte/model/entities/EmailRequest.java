package com.uniquindio.reporte.model.entities;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "emailRequest")
public class EmailRequest {
    private String forUser;
    private String body;
    private String route;
    private String code;
    private LocalDateTime expiresAt;// Solo necesario para el caso de archivo adjunto>
}
