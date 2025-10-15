package ru.example.micro.clientprocessing.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.example.micro.clientprocessing.model.*;
import ru.example.micro.clientprocessing.repository.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BlackListServiceTest {

    @Mock
    private BlackListRepository blackListRepository;
    @Mock
    private ClientRepository clientRepository;
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private BlackListService service;

    private BlackListEntry entry;

    @BeforeEach
    void init() {
        entry = new BlackListEntry();
        entry.setDocumentId("12345");
    }

    @Test
    void isBlocked_shouldReturnTrueIfExists() {
        when(blackListRepository.existsByDocumentId("doc")).thenReturn(true);
        assertTrue(service.isBlocked("doc"));
    }

    @Test
    void addToBlackList_shouldCreateNewEntryAndBlockUser() {
        when(blackListRepository.findByDocumentId("12345")).thenReturn(null);

        Client client = new Client();
        client.setUserId(1L);
        User user = new User();
        user.setRole(Role.CLIENT);

        when(clientRepository.findByDocumentId("12345")).thenReturn(Optional.of(client));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        BlackListEntry result = service.addToBlackList("12345", "fraud");

        verify(blackListRepository).save(any());
        verify(userRepository).save(user);
        assertEquals(Role.BLOCKED_CLIENT, user.getRole());
        assertEquals("12345", result.getDocumentId());
    }

    @Test
    void addToBlackList_shouldReturnExistingIfAlreadyPresent() {
        when(blackListRepository.findByDocumentId("123")).thenReturn(entry);
        BlackListEntry result = service.addToBlackList("123", "reason");
        assertEquals(entry, result);
        verify(blackListRepository, never()).save(any());
    }

    @Test
    void removeFromBlackList_shouldDeleteEntryAndUnblockUser() {
        when(blackListRepository.findByDocumentId("12345")).thenReturn(entry);
        Client client = new Client();
        client.setUserId(2L);
        User user = new User();
        user.setRole(Role.BLOCKED_CLIENT);

        when(clientRepository.findByDocumentId("12345")).thenReturn(Optional.of(client));
        when(userRepository.findById(2L)).thenReturn(Optional.of(user));

        service.removeFromBlackList("12345");

        verify(blackListRepository).delete(entry);
        verify(userRepository).save(user);
        assertEquals(Role.CURRENT_CLIENT, user.getRole());
    }

    @Test
    void removeFromBlackList_shouldDoNothingIfNotFound() {
        when(blackListRepository.findByDocumentId("nope")).thenReturn(null);
        service.removeFromBlackList("nope");
        verify(blackListRepository, never()).delete(any());
    }
}
