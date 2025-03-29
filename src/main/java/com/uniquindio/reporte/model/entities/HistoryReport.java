package com.uniquindio.reporte.model.entities;


import com.uniquindio.reporte.model.enums.reports.EnumStateReport;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
@Document(collection = "history_report")
public class HistoryReport {

//    @Id
//    private ObjectId id;

    private String observations;

    @Field(name = "client_Id")
    private ObjectId clienteId;

    private LocalDate date;

    @Field(name = "enumStateReport")
    private EnumStateReport enumStateReport;




}
