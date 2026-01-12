package com.example.driverconsumer.service;

import com.example.driverconsumer.dto.DriverLocationDTO;
import com.example.driverconsumer.model.DriverLocation;
import com.example.driverconsumer.model.DriverLocationHistory;
import com.example.driverconsumer.repository.LocationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class LocationConsumerService {

    private final LocationRepository repository;
    private final SimpMessagingTemplate messagingTemplate;
    private final org.springframework.data.mongodb.core.MongoTemplate mongoTemplate;

    @KafkaListener(topics = "driver-live-location", groupId = "driver-location-group")
    public void consumeLocation(DriverLocationDTO locationDTO) {
        log.info("Consumed location for driver: {}", locationDTO.getDriverId());
        processLocation(locationDTO);
    }

    /**
     * Process a location DTO: upsert latest, publish to WS topics, and persist history.
     * Can be called from Kafka listener or from inbound WebSocket controller.
     */
    public void processLocation(DriverLocationDTO locationDTO) {
        // Save to MongoDB (Upsert)
        DriverLocation driverLocation = DriverLocation.builder()
                .driverId(locationDTO.getDriverId())
                .latitude(locationDTO.getLatitude())
                .longitude(locationDTO.getLongitude())
                .timestamp(locationDTO.getTimestamp())
                .build();
        repository.save(driverLocation);

        // Send to WebSocket topic for real-time tracking
        try {
            log.info("Publishing to WebSocket topics for driver: {}", locationDTO.getDriverId());
            messagingTemplate.convertAndSend("/topic/driver-location/" + locationDTO.getDriverId(), locationDTO);
            messagingTemplate.convertAndSend("/topic/driver-locations", locationDTO); // Global topic
            log.info("Published to WebSocket topics for driver: {}", locationDTO.getDriverId());
        } catch (Exception e) {
            log.error("Error publishing to WebSocket topics for driver: {}", locationDTO.getDriverId(), e);
        }

        // Also persist every message into a history collection (one document per message)
        try {
            DriverLocationHistory history = DriverLocationHistory.builder()
                    .driverId(locationDTO.getDriverId())
                    .latitude(locationDTO.getLatitude())
                    .longitude(locationDTO.getLongitude())
                    .timestamp(locationDTO.getTimestamp())
                    .build();
            mongoTemplate.insert(history);
        } catch (Exception ex) {
            log.error("Failed to write history for driver: {}", locationDTO.getDriverId(), ex);
        }
    }
}
