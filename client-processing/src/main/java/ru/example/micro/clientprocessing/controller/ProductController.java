package ru.example.micro.clientprocessing.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.example.micro.clientprocessing.model.Product;
import ru.example.micro.clientprocessing.service.ProductService;
import ru.example.micro.logging.annotation.HttpIncomeRequestLog;
import ru.example.micro.logging.annotation.HttpOutcomeRequestLog;
import ru.example.micro.logging.annotation.Metric;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @HttpIncomeRequestLog
    @HttpOutcomeRequestLog
    @GetMapping
    @Metric
    public List<Product> getAll() {
        return service.getAll();
    }

    @HttpIncomeRequestLog
    @HttpOutcomeRequestLog
    @GetMapping("/{id}")
    @Metric
    public Product getById(@PathVariable("id") Long id) {
        return service.getById(id);
    }

    @HttpIncomeRequestLog
    @HttpOutcomeRequestLog
    @PostMapping
    @Metric
    public Product create(@RequestBody Product product) {
        return service.save(product);
    }

    @HttpIncomeRequestLog
    @HttpOutcomeRequestLog
    @PutMapping("/{id}")
    @Metric
    public Product update(@PathVariable("id") Long id, @RequestBody Product product) {
        product.setId(id);
        return service.save(product);
    }

    @HttpIncomeRequestLog
    @HttpOutcomeRequestLog
    @DeleteMapping("/{id}")
    @Metric
    public void delete(@PathVariable("id") Long id) {
        service.delete(id);
    }

    @HttpIncomeRequestLog
    @HttpOutcomeRequestLog
    @Metric
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleException(IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
