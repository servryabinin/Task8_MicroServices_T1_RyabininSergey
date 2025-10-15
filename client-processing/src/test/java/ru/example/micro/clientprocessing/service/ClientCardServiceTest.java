package ru.example.micro.clientprocessing.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;
import ru.example.micro.clientprocessing.model.ClientCard;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClientCardServiceTest {

    @Mock
    KafkaTemplate<String, String> kafkaTemplate;

    @Mock
    ObjectMapper objectMapper;

    @InjectMocks
    ClientCardService service;

    @Test
    void create_shouldSerializeAndSendKafkaMessage() throws Exception {
        ClientCard card = new ClientCard();
        when(objectMapper.writeValueAsString(card)).thenReturn("{json}");
        ClientCard result = service.create(card);

        verify(kafkaTemplate).send("client_cards", "{json}");
        assertEquals(card, result);
    }

    @Test
    void create_shouldThrowWhenSerializationFails() throws Exception {
        ClientCard card = new ClientCard();
        when(objectMapper.writeValueAsString(card)).thenThrow(new RuntimeException("boom"));
        assertThrows(RuntimeException.class, () -> service.create(card));
    }
}
