package ru.example.micro.accountprocessing.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.example.micro.accountprocessing.model.Card;
import ru.example.micro.accountprocessing.service.CardService;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class CardControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CardService cardService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetAll() throws Exception {
        Mockito.when(cardService.getAll()).thenReturn(List.of(new Card()));

        mockMvc.perform(get("/api/cards").with(user("user").roles("USER")))
                .andExpect(status().isOk());
    }

    @Test
    void testGetById() throws Exception {
        Card card = new Card();
        card.setId(1L);
        Mockito.when(cardService.getById(1L)).thenReturn(card);

        mockMvc.perform(get("/api/cards/1").with(user("user").roles("USER")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void testCreate() throws Exception {
        Card card = new Card();
        card.setId(1L);
        Mockito.when(cardService.save(any(Card.class))).thenReturn(card);

        mockMvc.perform(post("/api/cards")
                        .with(csrf())
                        .with(user("user").roles("USER"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(card)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void testUpdate() throws Exception {
        Card card = new Card();
        card.setId(1L);
        Mockito.when(cardService.save(any(Card.class))).thenReturn(card);

        mockMvc.perform(put("/api/cards/1")
                        .with(csrf())
                        .with(user("user").roles("USER"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(card)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void testDelete() throws Exception {
        mockMvc.perform(delete("/api/cards/1").with(csrf()).with(user("user").roles("USER")))
                .andExpect(status().isOk());

        Mockito.verify(cardService).delete(1L);
    }
}
