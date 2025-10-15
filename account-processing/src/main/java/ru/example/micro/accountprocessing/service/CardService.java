package ru.example.micro.accountprocessing.service;

import org.springframework.stereotype.Service;
import ru.example.micro.accountprocessing.model.Card;
import ru.example.micro.accountprocessing.repository.CardRepository;
import ru.example.micro.logging.annotation.Cached;
import ru.example.micro.logging.annotation.LogDatasourceError;
import ru.example.micro.logging.annotation.Metric;

import java.util.List;

@Service
public class CardService {
    private final CardRepository repository;

    public CardService(CardRepository repository) {
        this.repository = repository;
    }

    @LogDatasourceError
    @Metric
    @Cached
    public List<Card> getAll() {
        return repository.findAll();
    }

    @LogDatasourceError
    @Metric
    @Cached
    public Card getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @LogDatasourceError
    @Metric
    @Cached
    public Card save(Card card) {
        return repository.save(card);
    }

    @LogDatasourceError
    @Metric
    @Cached
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
