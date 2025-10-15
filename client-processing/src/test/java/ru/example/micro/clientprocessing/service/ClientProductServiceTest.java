package ru.example.micro.clientprocessing.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;
import ru.example.micro.clientprocessing.model.*;
import ru.example.micro.clientprocessing.repository.ClientProductRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClientProductServiceTest {

    @Mock
    private ClientProductRepository repository;
    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;
    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private ClientProductService service;

    private ClientProduct cp;

    @BeforeEach
    void setup() {
        cp = new ClientProduct();
        cp.setClientId(1L);
        cp.setProductId(2L);
        cp.setProductType(ProductType.DC);
        cp.setStatus(Status.ACTIVE);
    }

    @Test
    void getAll_shouldReturnList() {
        when(repository.findAll()).thenReturn(List.of(cp));
        assertEquals(1, service.getAll().size());
    }

    @Test
    void create_shouldValidateAndSaveAndSendKafka() throws Exception {
        when(repository.save(any())).thenReturn(cp);
        when(objectMapper.writeValueAsString(any())).thenReturn("{}");

        ClientProduct result = service.create(cp);

        verify(repository).save(any());
        verify(kafkaTemplate).send(eq("client_products"), anyString());
        assertNotNull(result.getOpenDate());
    }

    @Test
    void create_shouldThrowIfClientIdNull() {
        cp.setClientId(null);
        assertThrows(IllegalArgumentException.class, () -> service.create(cp));
    }

    @Test
    void update_shouldSendKafkaMessage() throws Exception {
        when(repository.save(any())).thenReturn(cp);
        when(objectMapper.writeValueAsString(any())).thenReturn("{}");

        ClientProduct result = service.update(99L, cp);

        assertEquals(99L, result.getId());
        verify(kafkaTemplate).send(any(), any());
    }

    @Test
    void delete_shouldCallRepositoryDeleteById() {
        service.delete(1L);
        verify(repository).deleteById(1L);
    }
}
