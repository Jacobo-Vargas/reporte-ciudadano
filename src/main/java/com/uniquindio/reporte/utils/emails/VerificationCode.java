package com.uniquindio.reporte.utils.emails;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class VerificationCode {
    private String code;
    private LocalDateTime expiresAt;
}