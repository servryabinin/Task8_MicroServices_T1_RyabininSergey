package ru.example.micro.accountprocessing.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.example.micro.accountprocessing.model.Payment;
import ru.example.micro.accountprocessing.repository.PaymentRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

    @Mock
    PaymentRepository repository;

    @InjectMocks
    PaymentService service;

    @Test
    void getAll_shouldReturnPayments() {
        when(repository.findAll()).thenReturn(List.of(new Payment(), new Payment()));
        assertEquals(2, service.getAll().size());
    }

    @Test
    void getById_shouldReturnPayment() {
        Payment p = new Payment(); p.setId(1L);
        when(repository.findById(1L)).thenReturn(Optional.of(p));
        assertEquals(1L, service.getById(1L).getId());
    }

    @Test
    void save_shouldSavePayment() {
        Payment p = new Payment();
        when(repository.save(p)).thenReturn(p);
        assertEquals(p, service.save(p));
    }

    @Test
    void delete_shouldDeletePayment() {
        service.delete(7L);
        verify(repository).deleteById(7L);
    }
}
