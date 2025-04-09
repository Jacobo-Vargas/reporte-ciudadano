package com.uniquindio.reporte.model.DTO.notification;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record UpdateNotificationDTO(String message,
                                    Boolean read,
                                    String reportId,
                                    String userId

) {}



