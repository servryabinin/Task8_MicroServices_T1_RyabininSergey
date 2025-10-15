package ru.example.micro.accountprocessing.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.example.micro.accountprocessing.model.Account;
import ru.example.micro.accountprocessing.repository.AccountRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Mock
    AccountRepository repository;

    @InjectMocks
    AccountService service;

    @Test
    void getAll_shouldReturnAccounts() {
        when(repository.findAll()).thenReturn(List.of(new Account(), new Account()));
        assertEquals(2, service.getAll().size());
        verify(repository).findAll();
    }

    @Test
    void getById_shouldReturnAccount() {
        Account a = new Account(); a.setId(1L);
        when(repository.findById(1L)).thenReturn(Optional.of(a));
        assertEquals(1L, service.getById(1L).getId());
    }

    @Test
    void getById_shouldReturnNullIfNotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());
        assertNull(service.getById(1L));
    }

    @Test
    void save_shouldCallRepositorySave() {
        Account a = new Account();
        when(repository.save(a)).thenReturn(a);
        assertEquals(a, service.save(a));
        verify(repository).save(a);
    }

    @Test
    void delete_shouldCallDeleteById() {
        service.delete(5L);
        verify(repository).deleteById(5L);
    }
}
