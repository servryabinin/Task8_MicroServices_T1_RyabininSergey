package ru.example.micro.creditprocessing.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.example.micro.creditprocessing.model.PaymentRegistry;
import ru.example.micro.creditprocessing.repository.PaymentRegistryRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentRegistryServiceTest {

    @Mock
    private PaymentRegistryRepository repository;

    @InjectMocks
    private PaymentRegistryService service;

    @Test
    void getAll_shouldReturnAllPayments() {
        when(repository.findAll()).thenReturn(List.of(new PaymentRegistry(), new PaymentRegistry()));

        List<PaymentRegistry> result = service.getAll();

        assertEquals(2, result.size());
        verify(repository).findAll();
    }

    @Test
    void getById_shouldReturnPaymentIfExists() {
        PaymentRegistry p = new PaymentRegistry();
        p.setId(1L);
        when(repository.findById(1L)).thenReturn(Optional.of(p));

        PaymentRegistry result = service.getById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(repository).findById(1L);
    }

    @Test
    void getById_shouldReturnNullIfNotFound() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        PaymentRegistry result = service.getById(99L);

        assertNull(result);
        verify(repository).findById(99L);
    }

    @Test
    void save_shouldCallRepositorySave() {
        PaymentRegistry p = new PaymentRegistry();
        when(repository.save(p)).thenReturn(p);

        PaymentRegistry result = service.save(p);

        assertEquals(p, result);
        verify(repository).save(p);
    }

    @Test
    void delete_shouldInvokeRepositoryDeleteById() {
        service.delete(10L);

        verify(repository).deleteById(10L);
    }
}
