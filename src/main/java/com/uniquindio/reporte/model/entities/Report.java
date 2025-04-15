package com.uniquindio.reporte.model.entities;

import com.uniquindio.reporte.model.enums.reports.EnumStatusReport;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;
import java.util.List;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
@Document(collection = "reports")
public class Report {

    @EqualsAndHashCode.Include
    @Id
    private ObjectId id;

    private List <HistoryReport> history;

    @Field(name="date_creation")
    private  LocalDate dateCreation;

    private String description;

    private int counterImportant;

    @Field(name="userId")
    private ObjectId userId;

    private String title;

    private Location location;

    @Field(name="category_id")
    private ObjectId categoryId;

    private  List<String> photos;

    private EnumStatusReport status;

}
