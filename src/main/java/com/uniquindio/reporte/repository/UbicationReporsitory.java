package com.uniquindio.reporte.repository;

import com.uniquindio.reporte.model.entities.Ubication;
import com.uniquindio.reporte.model.entities.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UbicationReporsitory extends MongoRepository<Ubication, ObjectId> {
}
