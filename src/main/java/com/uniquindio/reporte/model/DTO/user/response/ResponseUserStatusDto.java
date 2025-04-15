package com.uniquindio.reporte.model.DTO.user.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.uniquindio.reporte.model.enums.users.EnumUserStatus;

public record ResponseUserStatusDto (
        @JsonProperty("id")
        @JsonSerialize(using = ToStringSerializer.class)
        String id,
        String documentNumber,
        String name,
        EnumUserStatus enumUserStatus
) {
}
