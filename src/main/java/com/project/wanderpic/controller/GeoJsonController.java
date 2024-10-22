package com.project.wanderpic.controller;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GeoJsonController {

    private final ResourceLoader resourceLoader;

    public GeoJsonController(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @GetMapping("/api/geojson")
    public ResponseEntity<Resource> getGeoJson() {
        // 경로를 'static/sig.json'으로 변경
        Resource resource = resourceLoader.getResource("classpath:static/sig.json");
        return ResponseEntity.ok()
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(resource);
    }
}