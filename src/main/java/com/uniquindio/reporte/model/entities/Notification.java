package com.uniquindio.reporte.model.entities;

import com.uniquindio.reporte.model.enums.EnumStatusNotification;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
@Document(collection = "notification")
public class Notification {

    @EqualsAndHashCode.Include
    @Id
    @Field(name = "notification_id")
    private ObjectId id;

    private String message;

    private LocalDate date;

    private String type;

    private  Boolean read;

    @Field(name = "report_id")
    private ObjectId reportId;

    @Field(name = "user_id")
    private ObjectId userId;

    private EnumStatusNotification status;
}
