package com.evbooking.config;

import com.evbooking.model.Station;
import com.evbooking.repository.StationRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StationDataLoader {
    @Bean
    CommandLineRunner loadStations(StationRepository repository) {
        return args -> {
            if (repository.count() == 0) {
                repository.save(new Station("GreenCharge Hub", "Mylavaram, Andhra Pradesh", "DC CCS", 25, 4, "https://images.unsplash.com/photo-1593941707882-a5bba14938c7?auto=format&fit=crop&w=900&q=80"));
                repository.save(new Station("ChargePoint Mall", "Vijayawada, Andhra Pradesh", "AC Type 2", 15, 6, "https://images.unsplash.com/photo-1504222490345-c075b6008014?auto=format&fit=crop&w=900&q=80"));
                repository.save(new Station("EV Power Station", "Gannavaram, Andhra Pradesh", "DC CCS", 30, 3, "https://images.unsplash.com/photo-1635335872894-8c5d7a0a4e0a?auto=format&fit=crop&w=900&q=80"));
                repository.save(new Station("Sunrise Charging", "Poranki, Andhra Pradesh", "AC Type 2", 18, 5, "https://images.unsplash.com/photo-1593941707882-a5bba14938c7?auto=format&fit=crop&w=900&q=80"));
            }
        };
    }
}
