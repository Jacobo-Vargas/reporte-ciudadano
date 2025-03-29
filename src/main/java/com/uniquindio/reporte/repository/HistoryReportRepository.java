package com.uniquindio.reporte.repository;

import com.uniquindio.reporte.model.entities.HistoryReport;
import com.uniquindio.reporte.model.entities.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface HistoryReportRepository extends MongoRepository<HistoryReport, ObjectId> {
}
