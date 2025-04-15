package com.uniquindio.reporte.mapper;

import com.uniquindio.reporte.model.DTO.location.LocationDTO;
import com.uniquindio.reporte.model.entities.Location;
import com.uniquindio.reporte.utils.ObjectIdMapperUtil;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {ObjectIdMapperUtil.class})
public interface LocationMapper {

    Location toEntity(LocationDTO locationDTO);
}
