package com.uniquindio.reporte.mapper;

import com.uniquindio.reporte.model.DTO.notification.CreateNotificationDTO;
import com.uniquindio.reporte.model.DTO.notification.GeneralNotificationDTO;
import com.uniquindio.reporte.model.entities.Notification;
import com.uniquindio.reporte.utils.ObjectIdMapperUtil;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = ObjectIdMapperUtil.class)
public interface NotificationMapper {

    @Mapping(target = "date", expression = "java(java.time.LocalDate.now())")
    Notification toEntity(CreateNotificationDTO dto);

    GeneralNotificationDTO toDTO(Notification notification);

    List<GeneralNotificationDTO> toListDTO(List<Notification> notifications);
}
