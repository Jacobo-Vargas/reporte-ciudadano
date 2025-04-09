package com.uniquindio.reporte.repository;

import com.uniquindio.reporte.model.entities.Notification;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface NotificationRepository extends MongoRepository<Notification, ObjectId> {

    List<Notification> findByUserId(ObjectId userId);

}
