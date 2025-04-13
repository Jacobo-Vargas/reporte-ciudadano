package com.uniquindio.reporte.model.DTO.notification;

public record GeneralNotificationDTO(String id,
                                     String message,
                                     String date,
                                     String type,
                                     Boolean read,
                                     String reportId,
                                     String userId
) {
}