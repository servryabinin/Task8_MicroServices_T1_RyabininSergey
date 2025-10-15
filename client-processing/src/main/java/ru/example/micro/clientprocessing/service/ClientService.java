package ru.example.micro.clientprocessing.service;

import org.springframework.stereotype.Service;
import ru.example.micro.clientprocessing.model.Client;
import ru.example.micro.clientprocessing.repository.ClientRepository;
import ru.example.micro.logging.annotation.Cached;
import ru.example.micro.logging.annotation.LogDatasourceError;
import ru.example.micro.logging.annotation.Metric;

import java.util.List;

@Service
public class ClientService {
    private final ClientRepository repository;

    public ClientService(ClientRepository repository) {
        this.repository = repository;
    }

    @LogDatasourceError
    @Metric
    @Cached
    public List<Client> getAll() {
        return repository.findAll();
    }

    @LogDatasourceError
    @Metric
    @Cached
    public Client getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @LogDatasourceError
    @Metric
    @Cached
    public Client save(Client client) {
        return repository.save(client);
    }

    @LogDatasourceError
    @Metric
    @Cached
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
