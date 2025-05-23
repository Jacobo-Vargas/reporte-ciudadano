package com.uniquindio.reporte.repository;

import com.uniquindio.reporte.model.entities.Category;
import com.uniquindio.reporte.model.entities.Comment;
import com.uniquindio.reporte.model.entities.User;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Example;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentRepository extends MongoRepository<Comment, ObjectId> {

    boolean existsById(ObjectId id);
    void deleteById(ObjectId id);
    Optional<Comment> findById(String id);
}
