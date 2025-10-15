package ru.example.micro.creditprocessing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.client.RestTemplate;

@EntityScan(basePackages = {
        "ru.example.micro.creditprocessing.model",
        "ru.example.micro.logging.model"
})
@EnableJpaRepositories(basePackages = {
        "ru.example.micro.creditprocessing.repository",
        "ru.example.micro.logging.repository"
})
@SpringBootApplication
public class CreditProcessingApplication {
    public static void main(String[] args) {
        SpringApplication.run(CreditProcessingApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
