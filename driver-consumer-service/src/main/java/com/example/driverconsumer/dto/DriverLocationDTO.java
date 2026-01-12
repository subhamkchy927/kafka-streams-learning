package com.example.driverconsumer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DriverLocationDTO {
    private String driverId;
    private double latitude;
    private double longitude;
    private long timestamp;
}
