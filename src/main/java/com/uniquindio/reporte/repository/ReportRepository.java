package com.uniquindio.reporte.repository;

import com.uniquindio.reporte.model.entities.Report;
import com.uniquindio.reporte.model.entities.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ReportRepository extends MongoRepository<Report, ObjectId> {
}
