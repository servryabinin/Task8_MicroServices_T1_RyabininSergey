package ru.example.micro.clientprocessing.security;

import org.springframework.stereotype.Service;
import ru.example.micro.clientprocessing.model.User;
import ru.example.micro.clientprocessing.repository.UserRepository;
import ru.example.micro.security.util.JwtUtil;

import java.util.List;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    public String login(String login, String password) {
        User user = userRepository.findByLogin(login)
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (!user.getPassword().equals(password)) {
            throw new RuntimeException("Invalid credentials");
        }

        return jwtUtil.generateToken(
                user.getLogin(),
                List.of(user.getRole().name()), // роли из enum
                null
        );
    }
}
