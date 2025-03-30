package com.uniquindio.reporte.repository;

import com.uniquindio.reporte.model.entities.Category;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CategoryRepository extends MongoRepository<Category, ObjectId> {

    Optional<Category> findByName(String name);

}
