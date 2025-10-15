package ru.example.micro.clientprocessing.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.example.micro.clientprocessing.model.User;
import ru.example.micro.clientprocessing.repository.UserRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository repository;

    @InjectMocks
    private UserService service;

    @Test
    void getAll_shouldReturnList() {
        when(repository.findAll()).thenReturn(List.of(new User()));
        assertEquals(1, service.getAll().size());
    }

    @Test
    void getById_shouldReturnUser() {
        User user = new User();
        user.setId(1L);
        when(repository.findById(1L)).thenReturn(Optional.of(user));
        assertEquals(1L, service.getById(1L).getId());
    }

    @Test
    void save_shouldCallRepositorySave() {
        User u = new User();
        when(repository.save(u)).thenReturn(u);
        assertEquals(u, service.save(u));
    }

    @Test
    void delete_shouldInvokeDeleteById() {
        service.delete(1L);
        verify(repository).deleteById(1L);
    }
}
