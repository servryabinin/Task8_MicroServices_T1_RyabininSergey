package ru.example.micro.accountprocessing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.example.micro.accountprocessing.model.Payment;
import ru.example.micro.logging.annotation.Metric;

import java.time.LocalDateTime;
import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    @Metric
    boolean existsByAccountIdAndIsCreditTrueAndPayedAtIsNull(Long accountId);

    @Metric
    List<Payment> findByAccountIdAndIsCreditTrueAndPayedAtIsNullAndPaymentDateLessThanEqual(Long accountId, LocalDateTime paymentDate);

    @Metric
    List<Payment> findByAccountIdAndIsCreditTrueAndPayedAtIsNull(Long accountId);



}
