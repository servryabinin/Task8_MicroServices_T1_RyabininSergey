package ru.example.micro.accountprocessing.controller;

import org.springframework.web.bind.annotation.*;
import ru.example.micro.accountprocessing.model.Account;
import ru.example.micro.accountprocessing.service.AccountService;
import ru.example.micro.logging.annotation.HttpIncomeRequestLog;
import ru.example.micro.logging.annotation.HttpOutcomeRequestLog;
import ru.example.micro.logging.annotation.Metric;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {
    private final AccountService service;

    public AccountController(AccountService service) {
        this.service = service;
    }

    @HttpIncomeRequestLog
    @HttpOutcomeRequestLog
    @GetMapping
    @Metric
    public List<Account> getAll() {
        return service.getAll();
    }

    @HttpIncomeRequestLog
    @HttpOutcomeRequestLog
    @GetMapping("/{id}")
    @Metric
    public Account getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @HttpIncomeRequestLog
    @HttpOutcomeRequestLog
    @PostMapping
    @Metric
    public Account create(@RequestBody Account account) {
        return service.save(account);
    }

    @HttpIncomeRequestLog
    @HttpOutcomeRequestLog
    @PutMapping("/{id}")
    @Metric
    public Account update(@PathVariable Long id, @RequestBody Account account) {
        account.setId(id);
        return service.save(account);
    }

    @HttpIncomeRequestLog
    @HttpOutcomeRequestLog
    @DeleteMapping("/{id}")
    @Metric
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
