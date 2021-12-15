package com.example.bikesstations;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class BikesStationsApplication {

    public static void main(String[] args) {
        SpringApplication.run(BikesStationsApplication.class, args);
    }

}
