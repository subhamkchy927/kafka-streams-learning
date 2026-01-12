package com.example.driverconsumer.repository;

import com.example.driverconsumer.model.DriverLocation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends MongoRepository<DriverLocation, String> {
}
