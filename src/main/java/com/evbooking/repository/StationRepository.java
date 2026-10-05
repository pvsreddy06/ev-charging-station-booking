package com.evbooking.repository;

import com.evbooking.model.Station;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface StationRepository extends MongoRepository<Station, String> {
    List<Station> findByLocationContainingIgnoreCase(String location);
}
