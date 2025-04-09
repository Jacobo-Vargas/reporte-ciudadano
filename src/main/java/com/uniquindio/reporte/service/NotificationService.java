package com.uniquindio.reporte.service;

import com.uniquindio.reporte.model.DTO.notification.CreateNotificationDTO;
import com.uniquindio.reporte.model.DTO.notification.UpdateNotificationDTO;
import org.springframework.http.ResponseEntity;

public interface NotificationService {

    ResponseEntity<?> createNotification(CreateNotificationDTO dto);

    ResponseEntity<?> updateNotification(UpdateNotificationDTO dto);

    ResponseEntity<?> getNotificationById(String id);

    ResponseEntity<?> getAllNotifications();

    ResponseEntity<?> deleteNotification(String id);

}
