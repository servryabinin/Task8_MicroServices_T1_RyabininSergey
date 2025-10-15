package ru.example.micro.clientprocessing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.example.micro.clientprocessing.model.Client;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {
    Optional<Client> findByDocumentId(String documentId);
}
