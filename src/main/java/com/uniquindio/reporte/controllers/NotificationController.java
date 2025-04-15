package com.uniquindio.reporte.controllers;

import com.uniquindio.reporte.model.DTO.notification.CreateNotificationDTO;
import com.uniquindio.reporte.model.DTO.notification.UpdateNotificationDTO;
import com.uniquindio.reporte.service.NotificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notification")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping
    public ResponseEntity<?> createNotification(@RequestBody @Valid CreateNotificationDTO dto) {
        return notificationService.createNotification(dto);
    }

    @PutMapping
    public ResponseEntity<?> updateNotification(@RequestBody UpdateNotificationDTO dto) {
        return notificationService.updateNotification(dto);
    }

    @GetMapping
    public ResponseEntity<?> getById(@RequestParam String id) {
        return notificationService.getNotificationById(id);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAll() {
        return notificationService.getAllNotifications();
    }

    @DeleteMapping
    public ResponseEntity<?> delete(@RequestParam String id) {
        return notificationService.deleteNotification(id);
    }
}
