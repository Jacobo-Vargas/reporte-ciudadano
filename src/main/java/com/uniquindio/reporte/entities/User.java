package com.uniquindio.reporte.entities;

import com.uniquindio.reporte.entities.enums.users.EnumUserStatus;
import com.uniquindio.reporte.entities.enums.users.EnumUserType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import java.util.List;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
@Document(collection = "users")
//@CompoundIndex(name = "email_userType_idx", def = "{'email': 1, 'user_type': 1}")
public class User {

    @EqualsAndHashCode.Include
    @Id
    private ObjectId id;

    @Field(name = "first_name")
    @TextIndexed // Para permitir búsquedas textuales
    private String firstName;

    @Field(name = "middle_name")
    private String middleName;

    @Field(name = "second_last_name")
    private String secondLastName;

    @Field(name = "first_surname")
    private String firstSurname;

    @Field(name = "residence_city")
    private String residenceCity;

    @Indexed(unique = true) // Evita duplicados en la base de datos
    private String email;

    @Indexed // Mejora búsquedas por teléfono
    private String phone;

    private String address;
    private String password;

    @Field(name = "user_type")
    private EnumUserType userType;

    @Field(name = "user_status")
    private EnumUserStatus userStatus;

    @DBRef // Para referenciar usuarios seguidores en otra colección
    private List<User> followers;

    private int punctuation;
}
