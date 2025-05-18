package com.uniquindio.reporte.service.impl;

import com.uniquindio.reporte.exceptions.NotFoundException;
import com.uniquindio.reporte.mapper.NotificationMapper;
import com.uniquindio.reporte.model.DTO.notification.CreateNotificationDTO;
import com.uniquindio.reporte.model.DTO.notification.UpdateNotificationDTO;
import com.uniquindio.reporte.model.entities.Notification;
import com.uniquindio.reporte.model.enums.EnumStatusNotification;
import com.uniquindio.reporte.repository.NotificationRepository;
import com.uniquindio.reporte.service.NotificationService;
import com.uniquindio.reporte.utils.ObjectIdMapperUtil;
import com.uniquindio.reporte.utils.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;

    @Override
    public ResponseEntity<?> createNotification(CreateNotificationDTO dto) {
        Notification notification = notificationMapper.toEntity(dto);
        notificationRepository.save(notification);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDto(201, "Notificación creada con éxito", notificationMapper.toDTO(notification)));
    }

    @Override
    public ResponseEntity<?> updateNotification(UpdateNotificationDTO dto) {
        try {
            ObjectId reportId = ObjectIdMapperUtil.map(dto.reportId());
            ObjectId userId = ObjectIdMapperUtil.map(dto.userId());

            Notification notification = notificationRepository.findAll().stream()
                    .filter(n -> n.getReportId().equals(reportId) && n.getUserId().equals(userId))
                    .findFirst()
                    .orElseThrow(() -> new NotFoundException("No se encontró notificación con ese usuario y reporte"));

            if (notification.getStatus() == EnumStatusNotification.ELIMINADO) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ResponseDto(400, "Esta notificación ya fue eliminada", null));
            }

            if (dto.message() != null) notification.setMessage(dto.message());
            if (dto.read() != null) notification.setRead(dto.read());

            notificationRepository.save(notification);
            return ResponseEntity.ok(new ResponseDto(200, "Notificación actualizada con éxito", notificationMapper.toDTO(notification)));

        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseDto(404, e.getMessage(), null));
        }
    }

    @Override
    public ResponseEntity<?> getNotificationById(String id) {
        try {
            Notification notification = notificationRepository.findById(ObjectIdMapperUtil.map(id))
                    .orElseThrow(() -> new NotFoundException("No se encontró la notificación con id: " + id));

            if (notification.getStatus() == EnumStatusNotification.ELIMINADO) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ResponseDto(404, "Esta notificación ha sido eliminada", null));
            }

            return ResponseEntity.ok(new ResponseDto(200, "Notificación obtenida", notificationMapper.toDTO(notification)));
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseDto(404, e.getMessage(), null));
        }
    }

    @Override
    public ResponseEntity<?> getAllNotifications() {
        List<Notification> list = notificationRepository.findAll().stream()
                .filter(n -> n.getStatus() == EnumStatusNotification.ACTIVO)
                .toList();

        return ResponseEntity.ok(new ResponseDto(200, "Notificaciones obtenidas con éxito", notificationMapper.toListDTO(list)));
    }

    @Override
    public ResponseEntity<?> deleteNotification(String id) {
        try {
            Notification notification = notificationRepository.findById(ObjectIdMapperUtil.map(id))
                    .orElseThrow(() -> new NotFoundException("No se encontró la notificación con id: " + id));

            if (notification.getStatus() == EnumStatusNotification.ELIMINADO) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ResponseDto(400, "La notificación ya fue eliminada", null));
            }

            notification.setStatus(EnumStatusNotification.ELIMINADO);
            notificationRepository.save(notification);

            return ResponseEntity.ok(new ResponseDto(200, "Notificación eliminada con éxito", null));
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseDto(404, e.getMessage(), null));
        }
    }
}
