package com.uniquindio.reporte.model.entities;


import lombok.Data;
import lombok.EqualsAndHashCode;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
@Document(collection = "qualification")
public class Qualification {

    @EqualsAndHashCode.Include
    @Id
    @Field(name = "qualification_id")
    private ObjectId qualificationId;

    private int reaction;

    private User user;

    @Field(name ="report_id")
    private ObjectId reportId;

    @Field(name ="user_id")
    private ObjectId userId;
}
