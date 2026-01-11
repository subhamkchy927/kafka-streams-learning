# Driver Live Location Tracking System

This project is a microservice-based system for real-time driver location tracking using Spring Boot, Apache Kafka, and MongoDB.

## Architecture

1.  **driver-producer-service**: Exposes a POST API to accept location data and publishes it to the `driver-live-location` Kafka topic.
2.  **driver-consumer-service**: Consumes messages from Kafka, updates the latest location in MongoDB, and provides a REST API to fetch data. It also broadcasts updates via WebSockets for real-time UIs.

## Prerequisites

- Java 17+
- Maven
- Apache Kafka (Running on `localhost:9092`)
- MongoDB (Running on `localhost:27017`)

## Project Structure

```text
driver-location-kafka-learnings/
├── driver-producer-service/ (Port: 8081)
│   ├── src/main/java/com/example/driverproducer/
│   │   ├── config/ (Kafka Config)
│   │   ├── controller/ (POST /location/publish)
│   │   ├── dto/ (Data Transfer Objects)
│   │   └── service/ (Kafka Producer Logic)
│   └── src/main/resources/application.yml
└── driver-consumer-service/ (Port: 8082)
    ├── src/main/java/com/example/driverconsumer/
    │   ├── config/ (WebSocket Config)
    │   ├── controller/ (GET /location/{driverId})
    │   ├── dto/ (Message DTO)
    │   ├── model/ (MongoDB Entity)
    │   ├── repository/ (Mongo Repository)
    │   └── service/ (Kafka Consumer Logic)
    └── src/main/resources/
        ├── static/index.html (WebSocket UI)
        └── application.yml
```

## Running the Services

1.  **Start Kafka & MongoDB**: Ensure they are accessible at default ports.
2.  **Build All Services** (from root):
    ```bash
    mvn clean package -DskipTests
    ```
3.  **Start Producer**:
    ```bash
    cd driver-producer-service
    mvn spring-boot:run
    ```
4.  **Start Consumer**:
    ```bash
    cd driver-consumer-service
    mvn spring-boot:run
    ```

## Testing

### 1. Publish Location (Producer)
```bash
curl -X POST -H "Content-Type: application/json" -d '{
  "driverId": "driver-123",
  "latitude": 12.9716,
  "longitude": 77.5946,
  "timestamp": 1704987600000
}' http://localhost:8081/location/publish
```

### 2. Fetch Latest Location (Consumer REST API)
```bash
curl http://localhost:8082/location/driver-123
```

### 3. Real-time Tracking (WebSocket UI)
Open `http://localhost:8082/index.html` in your browser. Send multiple POST requests for the same `driverId` with different coordinates to see live updates.
