package com.uniquindio.reporte.model.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
@Document(collection = "qualification")
public class Qualification {

    @EqualsAndHashCode.Include
    @Id
    @Field(name = "qualification_id")
    private ObjectId id;

    private int reaction;

    @Field(name = "date_creation")
    private LocalDate dateCreation;

    @Field(name ="report_id")
    private ObjectId reportId;

    @Field(name ="user_id")
    private ObjectId userId;
}
