package com.uniquindio.reporte.model.entities;

import com.uniquindio.reporte.model.enums.users.EnumResidenceCity;
import com.uniquindio.reporte.model.enums.users.EnumUserStatus;
import com.uniquindio.reporte.model.enums.users.EnumUserType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;
import java.util.List;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
@Document(collection = "users")
public class User {

    @EqualsAndHashCode.Include
    @Id
    private ObjectId id;

    @Indexed(unique = true)
    @Field(name = "document_number")
    private String documentNumber;

    @Indexed(unique = true)
    private String name;

    @Field(name = "residence_city")
    private EnumResidenceCity residenceCity;

    @Indexed(unique = true) // Evita duplicados en la base de datos
    private String email;

    @Indexed (unique = true)// Mejora búsquedas por teléfono
    private String phone;

    private String address;

    private String password;

    @Field(name = "user_type")
    private EnumUserType userType;

    @Field(name = "user_status")
    private EnumUserStatus enumUserStatus;

    @DBRef // Para referenciar usuarios seguidores en otra colección
    private List<User> followers;

    private int score;

    @Field(name = "created_At")
    private LocalDate createdAt;

    @Field(name="email_request")
    private EmailRequest emailRequest;

    private boolean verification;



}
