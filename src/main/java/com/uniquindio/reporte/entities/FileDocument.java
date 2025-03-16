package com.uniquindio.reporte.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
@Document(collection = "file_document")
public class FileDocument {

    @EqualsAndHashCode.Include
    @Id
    ObjectId Id;
    String url;
    String name;
    String description;
    @Field(name="creation_date")
    LocalDate creationDate;
    User user;
}
