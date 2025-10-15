package ru.example.micro.clientprocessing.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.example.micro.clientprocessing.DTO.ClientRegistrationRequest;
import ru.example.micro.clientprocessing.model.Client;
import ru.example.micro.clientprocessing.model.User;
import ru.example.micro.clientprocessing.service.BlackListService;
import ru.example.micro.clientprocessing.service.ClientService;
import ru.example.micro.clientprocessing.service.UserService;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ClientControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClientService clientService;

    @MockBean
    private UserService userService;

    @MockBean
    private BlackListService blackListService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetAll() throws Exception {
        Mockito.when(clientService.getAll()).thenReturn(List.of(new Client()));

        mockMvc.perform(get("/api/clients").with(user("user").roles("USER")))
                .andExpect(status().isOk());
    }

    @Test
    void testCreate() throws Exception {
        Client client = new Client();
        Mockito.when(clientService.save(any(Client.class))).thenReturn(client);

        mockMvc.perform(post("/api/clients")
                        .with(csrf())
                        .with(user("user").roles("USER"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(client)))
                .andExpect(status().isOk());
    }

    @Test
    void testRegister() throws Exception {
        ClientRegistrationRequest request = new ClientRegistrationRequest();
        request.setDocumentId("123");
        request.setLogin("user");
        request.setPassword("pass");
        request.setEmail("test@test.com");

        Mockito.when(blackListService.isBlocked("123")).thenReturn(false);
        Mockito.when(userService.save(any(User.class))).thenAnswer(i -> i.getArgument(0));
        Mockito.when(clientService.save(any(Client.class))).thenAnswer(i -> i.getArgument(0));

        mockMvc.perform(post("/api/clients/register")
                        .with(csrf())
                        .with(user("user").roles("USER"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }
}
