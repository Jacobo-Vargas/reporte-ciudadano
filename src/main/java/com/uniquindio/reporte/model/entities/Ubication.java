package com.uniquindio.reporte.model.entities;


import lombok.Data;
import lombok.EqualsAndHashCode;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
@Document(collection = "ubication")
public class Ubication {

    @EqualsAndHashCode.Include
    @Id
    private ObjectId Id;
    private float latitude;
    private float longitude;
    private String name;
    private String description;

}
