package ru.example.micro.accountprocessing.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.example.micro.accountprocessing.model.Card;
import ru.example.micro.accountprocessing.repository.CardRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CardServiceTest {

    @Mock
    CardRepository repository;

    @InjectMocks
    CardService service;

    @Test
    void getAll_shouldReturnCards() {
        when(repository.findAll()).thenReturn(List.of(new Card(), new Card()));
        assertEquals(2, service.getAll().size());
    }

    @Test
    void getById_shouldReturnCard() {
        Card c = new Card(); c.setId(1L);
        when(repository.findById(1L)).thenReturn(Optional.of(c));
        assertEquals(1L, service.getById(1L).getId());
    }

    @Test
    void getById_shouldReturnNullIfNotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());
        assertNull(service.getById(1L));
    }

    @Test
    void save_shouldSaveCard() {
        Card c = new Card();
        when(repository.save(c)).thenReturn(c);
        assertEquals(c, service.save(c));
    }

    @Test
    void delete_shouldDeleteCard() {
        service.delete(5L);
        verify(repository).deleteById(5L);
    }
}
