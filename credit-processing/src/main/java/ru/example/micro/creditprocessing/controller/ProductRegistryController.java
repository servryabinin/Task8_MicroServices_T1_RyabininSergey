package ru.example.micro.creditprocessing.controller;

import org.springframework.web.bind.annotation.*;
import ru.example.micro.creditprocessing.model.ProductRegistry;
import ru.example.micro.creditprocessing.service.ProductRegistryService;
import ru.example.micro.logging.annotation.HttpIncomeRequestLog;
import ru.example.micro.logging.annotation.HttpOutcomeRequestLog;
import ru.example.micro.logging.annotation.Metric;

import java.util.List;

@RestController
@RequestMapping("/api/product-registries")
public class ProductRegistryController {
    private final ProductRegistryService service;

    public ProductRegistryController(ProductRegistryService service) {
        this.service = service;
    }

    @HttpIncomeRequestLog
    @HttpOutcomeRequestLog
    @GetMapping
    @Metric
    public List<ProductRegistry> getAll() { return service.getAll(); }

    @HttpIncomeRequestLog
    @HttpOutcomeRequestLog
    @GetMapping("/{id}")
    @Metric
    public ProductRegistry getById(@PathVariable Long id) { return service.getById(id); }

    @HttpIncomeRequestLog
    @HttpOutcomeRequestLog
    @PostMapping
    @Metric
    public ProductRegistry create(@RequestBody ProductRegistry registry) { return service.save(registry); }

    @HttpIncomeRequestLog
    @HttpOutcomeRequestLog
    @PutMapping("/{id}")
    @Metric
    public ProductRegistry update(@PathVariable Long id, @RequestBody ProductRegistry registry) {
        registry.setId(id);
        return service.save(registry);
    }

    @HttpIncomeRequestLog
    @HttpOutcomeRequestLog
    @DeleteMapping("/{id}")
    @Metric
    public void delete(@PathVariable Long id) { service.delete(id); }
}
