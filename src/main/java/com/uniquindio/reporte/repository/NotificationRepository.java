package com.uniquindio.reporte.repository;

import com.uniquindio.reporte.model.entities.Notification;
import com.uniquindio.reporte.model.entities.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NotificationRepository extends MongoRepository<Notification, ObjectId> {
}
