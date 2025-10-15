package ru.example.micro.security.token;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import ru.example.micro.security.util.JwtUtil;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ServiceTokenGenerator implements CommandLineRunner {

    private final JwtUtil jwtUtil;
    private final ServiceTokenHolder tokenHolder;
    private final Environment env;

    @Override
    public void run(String... args) {
        String existingToken = System.getenv("SERVICE_JWT");
        if (existingToken != null && !existingToken.isEmpty()) {
            tokenHolder.setToken(existingToken);
        } else {
            String token = jwtUtil.generateToken(
                    "service-account",
                    List.of("SERVICE"),
                    null
            );
            tokenHolder.setToken(token);
            System.out.println("=== SERVICE JWT TOKEN ===");
            System.out.println(token);
            System.out.println("=========================");
        }
    }
}
