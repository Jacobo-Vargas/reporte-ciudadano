package com.uniquindio.reporte.service.impl;

import com.uniquindio.reporte.mapper.LocationMapper;
import com.uniquindio.reporte.model.DTO.location.LocationDTO;
import com.uniquindio.reporte.model.entities.Location;
import com.uniquindio.reporte.repository.LocationRepository;
import com.uniquindio.reporte.service.LocationService;
import com.uniquindio.reporte.utils.ResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;
    private final LocationMapper locationMapper;


    @Override
    public ResponseEntity<?> createLocation(LocationDTO locationDTO) {
        Location location = locationMapper.toEntity(locationDTO);

        try {
            location = locationRepository.save(location);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseDto(200, "Ubicación registrada con éxito", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(500, "Ocurrió un error y no se pudo guardar la ubicación.", e.getMessage()));
        }
    }

    public Location saveLocation(LocationDTO locationDTO) {
        return locationRepository.save(locationMapper.toEntity(locationDTO));
    }
}
