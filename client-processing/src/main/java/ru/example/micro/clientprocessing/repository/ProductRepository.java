package ru.example.micro.clientprocessing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.example.micro.clientprocessing.model.Product;
import ru.example.micro.logging.annotation.Metric;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Metric
    boolean existsByProductKey(String productKey);
}
