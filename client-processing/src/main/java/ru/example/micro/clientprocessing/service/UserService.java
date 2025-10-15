package ru.example.micro.clientprocessing.service;

import org.springframework.stereotype.Service;
import ru.example.micro.clientprocessing.model.User;
import ru.example.micro.clientprocessing.repository.UserRepository;
import ru.example.micro.logging.annotation.Cached;
import ru.example.micro.logging.annotation.LogDatasourceError;
import ru.example.micro.logging.annotation.Metric;

import java.util.List;

@Service
public class UserService {
    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    @LogDatasourceError
    @Metric
    @Cached
    public List<User> getAll() {
        return repository.findAll();
    }

    @LogDatasourceError
    @Metric
    @Cached
    public User getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @LogDatasourceError
    @Metric
    @Cached
    public User save(User user) {
        return repository.save(user);
    }

    @LogDatasourceError
    @Metric
    @Cached
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
