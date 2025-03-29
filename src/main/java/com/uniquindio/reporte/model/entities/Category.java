package com.uniquindio.reporte.model.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
@Document(collection = "category")
public class Category {

    @EqualsAndHashCode.Include
    @Id
    private ObjectId Id;

    private String name;

    private String description;

    private String icon;

}
