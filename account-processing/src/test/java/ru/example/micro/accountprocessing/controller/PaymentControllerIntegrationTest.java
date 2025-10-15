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
import ru.example.micro.accountprocessing.model.Payment;
import ru.example.micro.accountprocessing.service.PaymentService;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class PaymentControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PaymentService paymentService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetAll() throws Exception {
        Mockito.when(paymentService.getAll()).thenReturn(List.of(new Payment()));

        mockMvc.perform(get("/api/payments").with(user("user").roles("USER")))
                .andExpect(status().isOk());
    }

    @Test
    void testGetById() throws Exception {
        Payment payment = new Payment();
        payment.setId(1L);
        Mockito.when(paymentService.getById(1L)).thenReturn(payment);

        mockMvc.perform(get("/api/payments/1").with(user("user").roles("USER")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void testCreate() throws Exception {
        Payment payment = new Payment();
        payment.setId(1L);
        Mockito.when(paymentService.save(any(Payment.class))).thenReturn(payment);

        mockMvc.perform(post("/api/payments")
                        .with(csrf())
                        .with(user("user").roles("USER"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(payment)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void testUpdate() throws Exception {
        Payment payment = new Payment();
        payment.setId(1L);
        Mockito.when(paymentService.save(any(Payment.class))).thenReturn(payment);

        mockMvc.perform(put("/api/payments/1")
                        .with(csrf())
                        .with(user("user").roles("USER"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(payment)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void testDelete() throws Exception {
        mockMvc.perform(delete("/api/payments/1").with(csrf()).with(user("user").roles("USER")))
                .andExpect(status().isOk());

        Mockito.verify(paymentService).delete(1L);
    }
}
