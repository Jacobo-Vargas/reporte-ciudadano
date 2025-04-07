package com.uniquindio.reporte.model;


import lombok.Data;
import lombok.EqualsAndHashCode;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
@Document(collection = "location")
public class Location {

    @EqualsAndHashCode.Include
    @Id
    private ObjectId id;
    private float latitude;
    private float longitude;
    private String name;
    private String description;

}
