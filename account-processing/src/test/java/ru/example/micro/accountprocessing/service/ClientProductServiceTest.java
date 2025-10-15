package ru.example.micro.accountprocessing.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.example.micro.accountprocessing.model.ClientProduct;
import ru.example.micro.accountprocessing.repository.ClientProductRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClientProductServiceTest {

    @Mock
    ClientProductRepository repository;

    @InjectMocks
    ClientProductService service;

    @Test
    void save_shouldSaveProduct() {
        ClientProduct p = new ClientProduct();
        when(repository.save(p)).thenReturn(p);
        assertEquals(p, service.save(p));
    }

}
