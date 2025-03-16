package com.uniquindio.reporte.entities;


import lombok.Data;
import lombok.EqualsAndHashCode;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
@Document(collection = "qualification")
public class Qualification {

    @EqualsAndHashCode.Include
    @Id
    ObjectId Id;
    int reaction;
    User user;
}
