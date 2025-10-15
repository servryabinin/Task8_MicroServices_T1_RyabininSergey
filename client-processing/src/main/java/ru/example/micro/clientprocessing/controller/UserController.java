package ru.example.micro.clientprocessing.controller;

import org.springframework.web.bind.annotation.*;
import ru.example.micro.clientprocessing.model.User;
import ru.example.micro.clientprocessing.service.UserService;
import ru.example.micro.logging.annotation.HttpIncomeRequestLog;
import ru.example.micro.logging.annotation.HttpOutcomeRequestLog;
import ru.example.micro.logging.annotation.Metric;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @HttpIncomeRequestLog
    @HttpOutcomeRequestLog
    @GetMapping
    @Metric
    public List<User> getAll() {
        return service.getAll();
    }

    @HttpIncomeRequestLog
    @HttpOutcomeRequestLog
    @GetMapping("/{id}")
    @Metric
    public User getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @HttpIncomeRequestLog
    @HttpOutcomeRequestLog
    @PostMapping
    @Metric
    public User create(@RequestBody User user) {
        return service.save(user);
    }

    @HttpIncomeRequestLog
    @HttpOutcomeRequestLog
    @PutMapping("/{id}")
    @Metric
    public User update(@PathVariable Long id, @RequestBody User user) {
        user.setId(id);
        return service.save(user);
    }

    @HttpIncomeRequestLog
    @HttpOutcomeRequestLog
    @DeleteMapping("/{id}")
    @Metric
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
