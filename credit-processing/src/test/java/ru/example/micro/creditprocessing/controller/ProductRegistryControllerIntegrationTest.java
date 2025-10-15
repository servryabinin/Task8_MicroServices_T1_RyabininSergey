package ru.example.micro.creditprocessing.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.example.micro.creditprocessing.model.ProductRegistry;
import ru.example.micro.creditprocessing.service.ProductRegistryService;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ProductRegistryControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductRegistryService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetAll() throws Exception {
        Mockito.when(service.getAll()).thenReturn(List.of(new ProductRegistry()));

        mockMvc.perform(get("/api/product-registries").with(user("user").roles("USER")))
                .andExpect(status().isOk());
    }

    @Test
    void testGetById() throws Exception {
        ProductRegistry registry = new ProductRegistry();
        registry.setId(1L);
        Mockito.when(service.getById(1L)).thenReturn(registry);

        mockMvc.perform(get("/api/product-registries/1").with(user("user").roles("USER")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void testCreate() throws Exception {
        ProductRegistry registry = new ProductRegistry();
        registry.setId(1L);
        Mockito.when(service.save(any(ProductRegistry.class))).thenReturn(registry);

        mockMvc.perform(post("/api/product-registries")
                        .with(csrf())
                        .with(user("user").roles("USER"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registry)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void testUpdate() throws Exception {
        ProductRegistry registry = new ProductRegistry();
        registry.setId(1L);
        Mockito.when(service.save(any(ProductRegistry.class))).thenReturn(registry);

        mockMvc.perform(put("/api/product-registries/1")
                        .with(csrf())
                        .with(user("user").roles("USER"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registry)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void testDelete() throws Exception {
        mockMvc.perform(delete("/api/product-registries/1")
                        .with(csrf())
                        .with(user("user").roles("USER")))
                .andExpect(status().isOk());

        Mockito.verify(service).delete(1L);
    }
}
