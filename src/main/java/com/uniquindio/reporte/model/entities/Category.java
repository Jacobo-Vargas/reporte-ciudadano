package com.uniquindio.reporte.model.entities;

import com.uniquindio.reporte.model.entities.enums.reports.EnumCategoryReport;
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
    ObjectId Id;
    String name;
    String description;
    @Field(name="activation_status")
    Boolean activationStatus;
    User user;
    @Field(name="category_report")
    EnumCategoryReport categoryReport;

}
