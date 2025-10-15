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
import ru.example.micro.accountprocessing.model.Transaction;
import ru.example.micro.accountprocessing.service.TransactionService;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class TransactionControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionService transactionService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetAll() throws Exception {
        Mockito.when(transactionService.getAll()).thenReturn(List.of(new Transaction()));

        mockMvc.perform(get("/api/transactions").with(user("user").roles("USER")))
                .andExpect(status().isOk());
    }

    @Test
    void testGetById() throws Exception {
        Transaction transaction = new Transaction();
        transaction.setId(1L);
        Mockito.when(transactionService.getById(1L)).thenReturn(transaction);

        mockMvc.perform(get("/api/transactions/1").with(user("user").roles("USER")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void testCreate() throws Exception {
        Transaction transaction = new Transaction();
        transaction.setId(1L);
        Mockito.when(transactionService.save(any(Transaction.class))).thenReturn(transaction);

        mockMvc.perform(post("/api/transactions")
                        .with(csrf())
                        .with(user("user").roles("USER"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transaction)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void testUpdate() throws Exception {
        Transaction transaction = new Transaction();
        transaction.setId(1L);
        Mockito.when(transactionService.save(any(Transaction.class))).thenReturn(transaction);

        mockMvc.perform(put("/api/transactions/1")
                        .with(csrf())
                        .with(user("user").roles("USER"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transaction)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void testDelete() throws Exception {
        mockMvc.perform(delete("/api/transactions/1").with(csrf()).with(user("user").roles("USER")))
                .andExpect(status().isOk());

        Mockito.verify(transactionService).delete(1L);
    }
}
