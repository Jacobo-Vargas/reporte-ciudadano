package com.uniquindio.reporte.model.entities;


import lombok.Data;
import lombok.EqualsAndHashCode;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
@Document(collection = "verification_email")
public class VerificationEmail {

    @EqualsAndHashCode.Include
    @Id
    ObjectId Id;
    User user;
    @Field(name="date_time")
    LocalDateTime dateTime;
}
