package ru.example.micro.accountprocessing.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.example.micro.accountprocessing.model.Transaction;
import ru.example.micro.accountprocessing.repository.TransactionRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @Mock
    TransactionRepository repository;

    @InjectMocks
    TransactionService service;

    @Test
    void getAll_shouldReturnTransactions() {
        when(repository.findAll()).thenReturn(List.of(new Transaction(), new Transaction()));
        assertEquals(2, service.getAll().size());
    }

    @Test
    void getById_shouldReturnTransaction() {
        Transaction t = new Transaction(); t.setId(1L);
        when(repository.findById(1L)).thenReturn(Optional.of(t));
        assertEquals(1L, service.getById(1L).getId());
    }

    @Test
    void save_shouldSaveTransaction() {
        Transaction t = new Transaction();
        when(repository.save(t)).thenReturn(t);
        assertEquals(t, service.save(t));
    }

    @Test
    void delete_shouldDeleteTransaction() {
        service.delete(9L);
        verify(repository).deleteById(9L);
    }
}
