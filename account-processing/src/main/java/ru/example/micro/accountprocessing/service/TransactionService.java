package ru.example.micro.accountprocessing.service;

import org.springframework.stereotype.Service;
import ru.example.micro.accountprocessing.model.Transaction;
import ru.example.micro.accountprocessing.repository.TransactionRepository;
import ru.example.micro.logging.annotation.Cached;
import ru.example.micro.logging.annotation.LogDatasourceError;
import ru.example.micro.logging.annotation.Metric;

import java.util.List;

@Service
public class TransactionService {
    private final TransactionRepository repository;

    public TransactionService(TransactionRepository repository) {
        this.repository = repository;
    }
    @LogDatasourceError
    @Metric
    @Cached
    public List<Transaction> getAll() {
        return repository.findAll();
    }
    @LogDatasourceError
    @Metric
    @Cached
    public Transaction getById(Long id) {
        return repository.findById(id).orElse(null);
    }
    @LogDatasourceError
    @Metric
    @Cached
    public Transaction save(Transaction transaction) {
        return repository.save(transaction);
    }
    @LogDatasourceError
    @Metric
    @Cached
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
