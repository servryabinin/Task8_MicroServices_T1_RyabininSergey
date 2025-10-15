package ru.example.micro.creditprocessing.service;

import org.springframework.stereotype.Service;
import ru.example.micro.creditprocessing.model.ProductRegistry;
import ru.example.micro.creditprocessing.repository.ProductRegistryRepository;
import ru.example.micro.logging.annotation.Cached;
import ru.example.micro.logging.annotation.LogDatasourceError;
import ru.example.micro.logging.annotation.Metric;

import java.util.List;

@Service
public class ProductRegistryService {
    private final ProductRegistryRepository repository;

    public ProductRegistryService(ProductRegistryRepository repository) {
        this.repository = repository;
    }

    @LogDatasourceError
    @Metric
    @Cached
    public List<ProductRegistry> getAll() { return repository.findAll(); }

    @LogDatasourceError
    @Metric
    @Cached
    public ProductRegistry getById(Long id) { return repository.findById(id).orElse(null); }

    @LogDatasourceError
    @Metric
    @Cached
    public ProductRegistry save(ProductRegistry registry) { return repository.save(registry); }

    @LogDatasourceError
    @Metric
    @Cached
    public void delete(Long id) { repository.deleteById(id); }
}
