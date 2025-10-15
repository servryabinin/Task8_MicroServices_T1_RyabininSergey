package ru.example.micro.clientprocessing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan(basePackages = {
        "ru.example.micro.clientprocessing.model",
        "ru.example.micro.logging.model"
})
@EnableJpaRepositories(basePackages = {
        "ru.example.micro.clientprocessing.repository",
        "ru.example.micro.logging.repository"
})
@SpringBootApplication
public class ClientProcessingApplication {
    public static void main(String[] args) {

        SpringApplication.run(ClientProcessingApplication.class, args);
    }
}
