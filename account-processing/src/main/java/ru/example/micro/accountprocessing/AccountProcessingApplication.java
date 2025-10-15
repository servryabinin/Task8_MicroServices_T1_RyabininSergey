package ru.example.micro.accountprocessing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = {
        "ru.example.micro.accountprocessing.model",
        "ru.example.micro.logging.model"
})
@EnableJpaRepositories(basePackages = {
        "ru.example.micro.accountprocessing.repository",
        "ru.example.micro.logging.repository"
})
public class AccountProcessingApplication {
    public static void main(String[] args) {
        SpringApplication.run(AccountProcessingApplication.class, args);
    }
}

