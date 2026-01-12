package com.example.driverproducer.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaProducerConfig {

    public static final String DRIVER_LIVE_LOCATION = "driver-live-location";

    @Bean
    public NewTopic driverLocationTopic() {
        return TopicBuilder.name(DRIVER_LIVE_LOCATION).partitions(3).replicas(1).build();
    }
}
