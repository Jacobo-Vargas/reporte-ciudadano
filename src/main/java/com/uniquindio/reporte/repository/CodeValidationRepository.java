package com.uniquindio.reporte.repository;

import com.uniquindio.reporte.model.entities.CodeValidation;
import com.uniquindio.reporte.model.entities.Comment;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CodeValidationRepository extends MongoRepository<CodeValidation, ObjectId> {
    // Método para buscar un usuario por el código de confirmación
    Optional<CodeValidation> findByCode(String confirmationCode);

    void deleteByCode(String code);
}
