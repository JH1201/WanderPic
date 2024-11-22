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

    @GetMapping("/api/ctprvn_geojson")
    public ResponseEntity<Resource> ctprvnGetGeoJson() {
        // 경로를 'static/ctprvn-wgs84.json'으로 설정
        Resource resource = resourceLoader.getResource("classpath:static/ctprvn-wgs84.json");
        return ResponseEntity.ok()
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(resource);
    }

    @GetMapping("/api/sig_geojson")
    public ResponseEntity<Resource> getGeoJson() {
        // 경로를 'static/sig.json'으로 설정
        Resource resource = resourceLoader.getResource("classpath:static/sig.shp_wgs84.json");
        return ResponseEntity.ok()
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(resource);
    }
}