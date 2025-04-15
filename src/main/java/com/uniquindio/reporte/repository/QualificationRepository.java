package com.uniquindio.reporte.repository;

import com.uniquindio.reporte.model.entities.Qualification;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface QualificationRepository extends MongoRepository<Qualification, ObjectId> {

    List<Qualification> findByReportId(ObjectId reportId);
    Optional<Qualification> findByReportIdAndUserId(ObjectId reportId, ObjectId userId);

}
