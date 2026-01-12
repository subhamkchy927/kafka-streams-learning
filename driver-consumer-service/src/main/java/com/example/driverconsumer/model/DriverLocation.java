package com.example.driverconsumer.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "driver_locations")
public class DriverLocation {
    @Id
    private String driverId;
    private double latitude;
    private double longitude;
    private long timestamp;
}
