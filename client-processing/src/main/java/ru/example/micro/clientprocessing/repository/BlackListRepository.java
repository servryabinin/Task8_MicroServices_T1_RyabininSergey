package ru.example.micro.clientprocessing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.example.micro.clientprocessing.model.BlackListEntry;
import ru.example.micro.logging.annotation.Metric;

public interface BlackListRepository extends JpaRepository<BlackListEntry, Long> {
    @Metric
    boolean existsByDocumentId(String documentId);
    @Metric
    BlackListEntry findByDocumentId(String documentId);
}
