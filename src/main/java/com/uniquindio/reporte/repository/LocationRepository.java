package com.uniquindio.reporte.repository;

import com.uniquindio.reporte.model.entities.Location;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LocationRepository extends MongoRepository<Location, ObjectId> {
}
