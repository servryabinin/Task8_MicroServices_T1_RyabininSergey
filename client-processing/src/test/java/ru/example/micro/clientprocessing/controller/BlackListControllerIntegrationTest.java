package ru.example.micro.clientprocessing.controller;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.example.micro.clientprocessing.model.BlackListEntry;
import ru.example.micro.clientprocessing.service.BlackListService;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class BlackListControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BlackListService service;

    @Test
    void testGetAll() throws Exception {
        Mockito.when(service.getAll()).thenReturn(List.of(new BlackListEntry()));

        mockMvc.perform(get("/api/blacklist").with(user("user").roles("USER")))
                .andExpect(status().isOk());
    }

    @Test
    void testAdd() throws Exception {
        BlackListEntry entry = new BlackListEntry();
        Mockito.when(service.addToBlackList(anyString(), anyString())).thenReturn(entry);

        mockMvc.perform(post("/api/blacklist")
                        .with(csrf())
                        .with(user("master").roles("MASTER"))
                        .param("documentId", "123")
                        .param("reason", "fraud"))
                .andExpect(status().isOk());
    }

    @Test
    void testRemove() throws Exception {
        mockMvc.perform(delete("/api/blacklist/123")
                        .with(csrf())
                        .with(user("grand").roles("GRAND_EMPLOYEE")))
                .andExpect(status().isNoContent());

        Mockito.verify(service).removeFromBlackList("123");
    }

    @Test
    void testCheck() throws Exception {
        Mockito.when(service.isBlocked("123")).thenReturn(true);

        mockMvc.perform(get("/api/blacklist/check/123").with(user("user").roles("USER")))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }
}
