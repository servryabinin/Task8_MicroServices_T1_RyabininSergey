package ru.example.micro.clientprocessing.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.example.micro.clientprocessing.DTO.ClientRegistrationRequest;
import ru.example.micro.clientprocessing.model.Client;
import ru.example.micro.clientprocessing.model.Role;
import ru.example.micro.clientprocessing.model.User;
import ru.example.micro.clientprocessing.service.BlackListService;
import ru.example.micro.clientprocessing.service.ClientService;
import ru.example.micro.clientprocessing.service.UserService;
import ru.example.micro.logging.annotation.HttpIncomeRequestLog;
import ru.example.micro.logging.annotation.HttpOutcomeRequestLog;
import ru.example.micro.logging.annotation.Metric;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    private final ClientService clientService;
    private final UserService userService;
    private final BlackListService blackListService;

    public ClientController(ClientService clientService, UserService userService, BlackListService blackListService) {
        this.clientService = clientService;
        this.userService = userService;
        this.blackListService = blackListService;
    }

    @HttpIncomeRequestLog
    @HttpOutcomeRequestLog
    @GetMapping
    @Metric
    public List<Client> getAll() {
        return clientService.getAll();
    }

    @HttpIncomeRequestLog
    @HttpOutcomeRequestLog
    @GetMapping("/{id}")
    @Metric
    public Client getById(@PathVariable Long id) {
        return clientService.getById(id);
    }

    @HttpIncomeRequestLog
    @HttpOutcomeRequestLog
    @PostMapping
    @Metric
    public Client create(@RequestBody Client client) {
        return clientService.save(client);
    }

    @HttpIncomeRequestLog
    @HttpOutcomeRequestLog
    @PutMapping("/{id}")
    @Metric
    public Client update(@PathVariable Long id, @RequestBody Client client) {
        client.setId(id);
        return clientService.save(client);
    }

    @HttpIncomeRequestLog
    @HttpOutcomeRequestLog
    @DeleteMapping("/{id}")
    @Metric
    public void delete(@PathVariable Long id) {
        clientService.delete(id);
    }

    @HttpIncomeRequestLog
    @HttpOutcomeRequestLog
    @PostMapping("/register")
    @Metric
    public ResponseEntity<?> register(@RequestBody ClientRegistrationRequest request) {
        if (blackListService.isBlocked(request.getDocumentId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Client " + request.getDocumentId() + " is in the black list");
        }

        User user = new User();
        user.setLogin(request.getLogin());
        user.setPassword(request.getPassword());
        user.setEmail(request.getEmail());
        // назначаем роль CURRENT_CLIENT при создании
        user.setRole(Role.CURRENT_CLIENT);
        userService.save(user);

        Client client = new Client();
        client.setFirstName(request.getFirstName());
        client.setLastName(request.getLastName());
        client.setDocumentId(request.getDocumentId());
        client.setUserId(user.getId());
        client.setClientId(generateClientId());

        clientService.save(client);

        return ResponseEntity.ok(user);
    }


    private String generateClientId() {
        return "77" + System.currentTimeMillis() % 10000000000L;
    }
}
