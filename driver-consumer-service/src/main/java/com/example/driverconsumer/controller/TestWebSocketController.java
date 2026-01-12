package com.example.driverconsumer.controller;

import com.example.driverconsumer.dto.DriverLocationDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestWebSocketController {

    private final SimpMessagingTemplate messagingTemplate;

//    @GetMapping("/publish")
    public ResponseEntity<?> publishTest(@RequestParam(defaultValue = "test-driver") String driverId,
                                         @RequestParam(defaultValue = "12.9716") double latitude,
                                         @RequestParam(defaultValue = "77.5946") double longitude) {
        DriverLocationDTO dto = new DriverLocationDTO(driverId, latitude, longitude, System.currentTimeMillis());

        messagingTemplate.convertAndSend("/topic/driver-location/" + driverId, dto);
        messagingTemplate.convertAndSend("/topic/driver-locations", dto);

        Map<String, Object> resp = new HashMap<>();
        resp.put("message", "published to websocket topics");
        resp.put("payload", dto);
        return ResponseEntity.ok(resp);
    }
}
