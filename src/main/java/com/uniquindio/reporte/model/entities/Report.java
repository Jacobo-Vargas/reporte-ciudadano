package com.uniquindio.reporte.model.entities;

import com.uniquindio.reporte.model.entities.enums.reports.EnumState;
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
    ObjectId Id;
    String title;
    String description;
    @Field(name="file_document")
    List<FileDocument> fileDocument;
    @Field(name="date_creation")
    LocalDate dateCreation;
    User user;
    @Field(name="comments_list")
    List<Comment> commentsList;
    @Field(name="list_notificarions")
    List<Notification> listNotificarions;
    @Field(name="list_qualifications")
    List<Qualification> listQualifications;
    Ubication ubication;
    EnumState state;
    Category category;





}
