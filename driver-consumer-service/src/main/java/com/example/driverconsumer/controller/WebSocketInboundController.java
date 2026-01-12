package com.example.driverconsumer.controller;

import com.example.driverconsumer.dto.DriverLocationDTO;
import com.example.driverconsumer.service.LocationConsumerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
@Slf4j
public class WebSocketInboundController {

    private final LocationConsumerService consumerService;

    @MessageMapping("/publish")
    public void receiveFromWebSocket(@Payload DriverLocationDTO locationDTO) {
        log.info("Received location via WebSocket for driver: {}", locationDTO.getDriverId());
        consumerService.processLocation(locationDTO);
    }
}
