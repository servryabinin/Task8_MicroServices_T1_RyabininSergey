package ru.example.micro.security.token;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class ServiceTokenHolder {
    private String token;
}
