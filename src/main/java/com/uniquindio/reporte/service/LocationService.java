package com.uniquindio.reporte.service;

import com.uniquindio.reporte.model.DTO.location.LocationDTO;
import org.springframework.http.ResponseEntity;

public interface LocationService {

    ResponseEntity<?> createLocation(LocationDTO locationDTO);
}
