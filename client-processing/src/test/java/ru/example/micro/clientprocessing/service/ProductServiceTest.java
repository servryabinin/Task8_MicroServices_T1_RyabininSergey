package ru.example.micro.clientprocessing.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.example.micro.clientprocessing.model.Product;
import ru.example.micro.clientprocessing.repository.ProductRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository repository;

    @InjectMocks
    private ProductService service;

    @Test
    void save_shouldSetCreateDateAndSaveNewProduct() {
        Product p = new Product();
        p.setProductKey("key1");
        when(repository.existsByProductKey("key1")).thenReturn(false);
        when(repository.save(p)).thenReturn(p);

        Product result = service.save(p);

        assertNotNull(result.getCreateDate());
        verify(repository).save(p);
    }

    @Test
    void save_shouldThrowIfKeyNotUnique() {
        Product p = new Product();
        p.setProductKey("duplicate");
        when(repository.existsByProductKey("duplicate")).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> service.save(p));
    }

    @Test
    void getById_shouldReturnProduct() {
        Product p = new Product();
        p.setId(1L);
        when(repository.findById(1L)).thenReturn(Optional.of(p));
        assertEquals(1L, service.getById(1L).getId());
    }

    @Test
    void delete_shouldInvokeRepository() {
        service.delete(5L);
        verify(repository).deleteById(5L);
    }
}
