package ru.example.micro.accountprocessing.service;

import org.springframework.stereotype.Service;
import ru.example.micro.accountprocessing.model.Payment;
import ru.example.micro.accountprocessing.repository.PaymentRepository;
import ru.example.micro.logging.annotation.Cached;
import ru.example.micro.logging.annotation.LogDatasourceError;
import ru.example.micro.logging.annotation.Metric;

import java.util.List;

@Service
public class PaymentService {
    private final PaymentRepository repository;

    public PaymentService(PaymentRepository repository) {
        this.repository = repository;
    }

    @LogDatasourceError
    @Metric
    @Cached
    public List<Payment> getAll() {
        return repository.findAll();
    }

    @LogDatasourceError
    @Metric
    @Cached
    public Payment getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @LogDatasourceError
    @Metric
    @Cached
    public Payment save(Payment payment) {
        return repository.save(payment);
    }

    @LogDatasourceError
    @Metric
    @Cached
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
