package com.uniquindio.reporte.model.entities;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldNameConstants;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;
import java.time.LocalDateTime;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
@Document(collection = "comment")
public class Comment {

    @EqualsAndHashCode.Include
    @Id
    @Field(name="comment_id")
    private ObjectId commentId;

    private String message;

    private LocalDate date;

    @Field(name = "client_id")
    private ObjectId clientId;

    @Field(name = "report_id")
    private ObjectId reportId;



}
