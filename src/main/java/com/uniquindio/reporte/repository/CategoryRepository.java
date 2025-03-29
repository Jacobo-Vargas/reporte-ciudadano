package com.uniquindio.reporte.repository;

import com.uniquindio.reporte.model.entities.Category;
import com.uniquindio.reporte.model.entities.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CategoryRepository extends MongoRepository<Category, ObjectId> {
}
