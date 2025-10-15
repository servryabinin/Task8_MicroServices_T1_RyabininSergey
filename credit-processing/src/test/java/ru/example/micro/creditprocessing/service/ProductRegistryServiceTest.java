package ru.example.micro.creditprocessing.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.example.micro.creditprocessing.model.ProductRegistry;
import ru.example.micro.creditprocessing.repository.ProductRegistryRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductRegistryServiceTest {

    @Mock
    private ProductRegistryRepository repository;

    @InjectMocks
    private ProductRegistryService service;

    @Test
    void getAll_shouldReturnAllProducts() {
        when(repository.findAll()).thenReturn(List.of(new ProductRegistry()));

        List<ProductRegistry> result = service.getAll();

        assertEquals(1, result.size());
        verify(repository).findAll();
    }

    @Test
    void getById_shouldReturnProductIfExists() {
        ProductRegistry p = new ProductRegistry();
        p.setId(5L);
        when(repository.findById(5L)).thenReturn(Optional.of(p));

        ProductRegistry result = service.getById(5L);

        assertNotNull(result);
        assertEquals(5L, result.getId());
        verify(repository).findById(5L);
    }

    @Test
    void getById_shouldReturnNullIfNotFound() {
        when(repository.findById(123L)).thenReturn(Optional.empty());

        ProductRegistry result = service.getById(123L);

        assertNull(result);
        verify(repository).findById(123L);
    }

    @Test
    void save_shouldCallRepositorySave() {
        ProductRegistry p = new ProductRegistry();
        when(repository.save(p)).thenReturn(p);

        ProductRegistry result = service.save(p);

        assertEquals(p, result);
        verify(repository).save(p);
    }

    @Test
    void delete_shouldInvokeRepositoryDeleteById() {
        service.delete(77L);

        verify(repository).deleteById(77L);
    }
}
