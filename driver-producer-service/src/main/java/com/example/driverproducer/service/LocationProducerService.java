package com.example.driverproducer.service;

import com.example.driverproducer.config.KafkaProducerConfig;
import com.example.driverproducer.dto.DriverLocationDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class LocationProducerService {

    private final KafkaTemplate<String, DriverLocationDTO> kafkaTemplate;

    public void publishLocations(List<DriverLocationDTO> locations) {
        log.info("Publishing {} driver locations", locations.size());
        for (DriverLocationDTO dto : locations) {
            kafkaTemplate.send(KafkaProducerConfig.DRIVER_LIVE_LOCATION, dto.getDriverId(), dto);
            log.debug("Published location for driver {}", dto.getDriverId());
            kafkaTemplate.flush();
        }
    }
}