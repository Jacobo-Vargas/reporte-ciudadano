package com.uniquindio.reporte.model.entities;


import com.uniquindio.reporte.model.enums.EnumStatusComment;
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
    private ObjectId id;

    private String message;

    @Field(name = "created_At")
    private LocalDate createdAt;

    @Field(name = "user_id")
    private String userId;

    @Field(name = "report_id")
    private String reportId;

    private EnumStatusComment status;
}
