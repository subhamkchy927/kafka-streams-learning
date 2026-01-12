package com.example.driverproducer.controller;

import com.example.driverproducer.dto.DriverLocationDTO;
import com.example.driverproducer.service.LocationProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/location")
@RequiredArgsConstructor
public class LocationController {

    private final LocationProducerService producerService;

    @PostMapping("/locations")
    public ResponseEntity<String> publish(@RequestBody List<DriverLocationDTO> locations) {
        producerService.publishLocations(locations);
        return ResponseEntity.ok("Locations published successfully");
    }
}