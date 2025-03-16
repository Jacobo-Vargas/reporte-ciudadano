package com.uniquindio.reporte.model.entities;


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
    ObjectId Id;
    String message;
    @Field(name="shipping_date")
    LocalDate shippingDate;
    User user;
    Report report;
}
