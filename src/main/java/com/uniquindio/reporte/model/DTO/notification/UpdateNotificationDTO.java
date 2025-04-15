package com.uniquindio.reporte.model.DTO.notification;

public record UpdateNotificationDTO(String message,
                                    Boolean read,
                                    String reportId,
                                    String userId

) {
}