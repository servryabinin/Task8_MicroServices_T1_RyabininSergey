package ru.example.micro.clientprocessing.controller;

import org.springframework.web.bind.annotation.*;
import ru.example.micro.clientprocessing.model.ClientProduct;
import ru.example.micro.clientprocessing.service.ClientProductService;
import ru.example.micro.logging.annotation.HttpIncomeRequestLog;
import ru.example.micro.logging.annotation.HttpOutcomeRequestLog;
import ru.example.micro.logging.annotation.Metric;

import java.util.List;

@RestController
@RequestMapping("/api/client-products")
public class ClientProductController {

    private final ClientProductService service;

    public ClientProductController(ClientProductService service) {
        this.service = service;
    }

    @HttpIncomeRequestLog
    @HttpOutcomeRequestLog
    @GetMapping
    @Metric
    public List<ClientProduct> getAll() {
        return service.getAll();
    }

    @HttpIncomeRequestLog
    @HttpOutcomeRequestLog
    @GetMapping("/{id}")
    @Metric
    public ClientProduct getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @HttpIncomeRequestLog
    @HttpOutcomeRequestLog
    @PostMapping
    @Metric
    public ClientProduct create(@RequestBody ClientProduct clientProduct) {
        return service.create(clientProduct);
    }

    @HttpIncomeRequestLog
    @HttpOutcomeRequestLog
    @PutMapping("/{id}")
    @Metric
    public ClientProduct update(@PathVariable Long id, @RequestBody ClientProduct clientProduct) {
        return service.update(id, clientProduct);
    }

    @HttpIncomeRequestLog
    @HttpOutcomeRequestLog
    @DeleteMapping("/{id}")
    @Metric
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
