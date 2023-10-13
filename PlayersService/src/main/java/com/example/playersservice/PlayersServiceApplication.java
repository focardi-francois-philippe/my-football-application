package com.example.playersservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class PlayersServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PlayersServiceApplication.class, args);
    }

}
