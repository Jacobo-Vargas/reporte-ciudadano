package com.uniquindio.reporte.repository;

import com.uniquindio.reporte.model.entities.Report;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportRepository extends MongoRepository<Report, ObjectId> {

    List<Report> findByStatus(String status);
}
