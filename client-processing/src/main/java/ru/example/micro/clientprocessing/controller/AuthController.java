package ru.example.micro.clientprocessing.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.example.micro.clientprocessing.security.AuthService;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody Map<String, String> request) {
        String login = request.get("login");
        String password = request.get("password");
        String token = authService.login(login, password);
        return ResponseEntity.ok(Map.of("token", token));
    }
}
