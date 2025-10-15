package ru.example.micro.accountprocessing.controller;

import org.springframework.web.bind.annotation.*;
import ru.example.micro.accountprocessing.model.Payment;
import ru.example.micro.accountprocessing.service.PaymentService;
import ru.example.micro.logging.annotation.HttpIncomeRequestLog;
import ru.example.micro.logging.annotation.HttpOutcomeRequestLog;
import ru.example.micro.logging.annotation.Metric;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {
    private final PaymentService service;

    public PaymentController(PaymentService service) {
        this.service = service;
    }

    @HttpIncomeRequestLog
    @HttpOutcomeRequestLog
    @GetMapping
    @Metric
    public List<Payment> getAll() {
        return service.getAll();
    }

    @HttpIncomeRequestLog
    @HttpOutcomeRequestLog
    @GetMapping("/{id}")
    @Metric
    public Payment getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @HttpIncomeRequestLog
    @HttpOutcomeRequestLog
    @PostMapping
    @Metric
    public Payment create(@RequestBody Payment payment) {
        return service.save(payment);
    }

    @HttpIncomeRequestLog
    @HttpOutcomeRequestLog
    @PutMapping("/{id}")
    @Metric
    public Payment update(@PathVariable Long id, @RequestBody Payment payment) {
        payment.setId(id);
        return service.save(payment);
    }

    @HttpIncomeRequestLog
    @HttpOutcomeRequestLog
    @DeleteMapping("/{id}")
    @Metric
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
