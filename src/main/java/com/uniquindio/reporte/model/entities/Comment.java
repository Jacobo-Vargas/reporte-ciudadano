package com.uniquindio.reporte.model.entities;


import lombok.Data;
import lombok.EqualsAndHashCode;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
@Document(collection = "comment")
public class Comment {

    @EqualsAndHashCode.Include
    @Id
    ObjectId Id;
    String content;
    @Indexed // Mejora el rendimiento en consultas por fecha
    @Field(name = "date")
    private LocalDateTime date;

    @DBRef(lazy = true) // Carga diferida del usuario para mejorar el rendimiento
    private User user;
}
