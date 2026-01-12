package com.example.driverconsumer.controller;

import com.example.driverconsumer.model.DriverLocation;
import com.example.driverconsumer.repository.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/location")
@RequiredArgsConstructor
public class LocationController {

    private final LocationRepository repository;

    @GetMapping("/{driverId}")
    public ResponseEntity<DriverLocation> getLatestLocation(@PathVariable String driverId) {
        return repository.findById(driverId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
