package com.uniquindio.reporte.utils;

import org.bson.types.ObjectId;

public class ObjectIdMapperUtil {

    public static String map(ObjectId value) {
        return value != null ? value.toString() : null;
    }

    public static ObjectId map(String value) {
        return value != null ? new ObjectId(value) : null;
    }
}
