package ru.example.micro.clientprocessing.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.example.micro.clientprocessing.model.Client;
import ru.example.micro.clientprocessing.repository.ClientRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClientServiceTest {

    @Mock
    private ClientRepository repository;

    @InjectMocks
    private ClientService service;

    @Test
    void getAll_shouldReturnList() {
        when(repository.findAll()).thenReturn(List.of(new Client()));
        assertEquals(1, service.getAll().size());
        verify(repository).findAll();
    }

    @Test
    void getById_shouldReturnClient() {
        Client c = new Client();
        c.setId(1L);
        when(repository.findById(1L)).thenReturn(Optional.of(c));
        assertEquals(1L, service.getById(1L).getId());
    }

    @Test
    void save_shouldDelegateToRepository() {
        Client c = new Client();
        when(repository.save(c)).thenReturn(c);
        assertEquals(c, service.save(c));
    }

    @Test
    void delete_shouldInvokeDeleteById() {
        service.delete(10L);
        verify(repository).deleteById(10L);
    }
}
