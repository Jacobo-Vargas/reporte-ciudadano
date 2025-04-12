package com.uniquindio.reporte.controllers;

import com.uniquindio.reporte.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/imagenes")
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @PostMapping("/subir")
    public ResponseEntity<?> subirImagen(@RequestParam("archivo") MultipartFile archivo) {
        try {
            String url = imageService.uploadFile(archivo);
            return ResponseEntity.ok(Map.of("url", url));
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error subiendo imagen: " + e.getMessage());
        }
    }
}