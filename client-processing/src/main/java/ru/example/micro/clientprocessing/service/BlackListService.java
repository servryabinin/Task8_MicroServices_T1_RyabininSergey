package ru.example.micro.clientprocessing.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.example.micro.clientprocessing.model.BlackListEntry;
import ru.example.micro.clientprocessing.model.Client;
import ru.example.micro.clientprocessing.model.Role;
import ru.example.micro.clientprocessing.model.User;
import ru.example.micro.clientprocessing.repository.BlackListRepository;
import ru.example.micro.clientprocessing.repository.ClientRepository;
import ru.example.micro.clientprocessing.repository.UserRepository;
import ru.example.micro.logging.annotation.Cached;
import ru.example.micro.logging.annotation.LogDatasourceError;
import ru.example.micro.logging.annotation.Metric;

import java.util.List;
import java.util.Optional;

@Service
public class BlackListService {

    private final BlackListRepository repository;
    private final ClientRepository clientRepository;
    private final UserRepository userRepository;

    public BlackListService(BlackListRepository repository,
                            ClientRepository clientRepository,
                            UserRepository userRepository) {
        this.repository = repository;
        this.clientRepository = clientRepository;
        this.userRepository = userRepository;
    }

    @LogDatasourceError
    @Metric
    @Cached
    public boolean isBlocked(String documentId) {
        return repository.existsByDocumentId(documentId);
    }

    @LogDatasourceError
    @Metric
    @Transactional
    public BlackListEntry addToBlackList(String documentId, String reason) {
        BlackListEntry existing = repository.findByDocumentId(documentId);
        if (existing != null) {
            return existing;
        }

        BlackListEntry entry = new BlackListEntry();
        entry.setDocumentId(documentId);
        entry.setReason(reason);
        repository.save(entry);

        blockUserByDocumentId(documentId);
        return entry;
    }

    @LogDatasourceError
    @Metric
    @Transactional
    public void removeFromBlackList(String documentId) {
        BlackListEntry entry = repository.findByDocumentId(documentId);
        if (entry != null) {
            repository.delete(entry);
            unblockUserByDocumentId(documentId);
        }
    }

    @LogDatasourceError
    @Metric
    @Cached
    public List<BlackListEntry> getAll() {
        return repository.findAll();
    }

    private void blockUserByDocumentId(String documentId) {
        Optional<Client> clientOpt = clientRepository.findByDocumentId(documentId);
        clientOpt.ifPresent(client -> {
            Long userId = client.getUserId();
            if (userId != null) {
                userRepository.findById(userId).ifPresent(user -> {
                    user.setRole(Role.BLOCKED_CLIENT);
                    userRepository.save(user);
                });
            }
        });
    }

    private void unblockUserByDocumentId(String documentId) {
        Optional<Client> clientOpt = clientRepository.findByDocumentId(documentId);
        clientOpt.ifPresent(client -> {
            Long userId = client.getUserId();
            if (userId != null) {
                userRepository.findById(userId).ifPresent(user -> {
                    user.setRole(Role.CURRENT_CLIENT);
                    userRepository.save(user);
                });
            }
        });
    }
}
