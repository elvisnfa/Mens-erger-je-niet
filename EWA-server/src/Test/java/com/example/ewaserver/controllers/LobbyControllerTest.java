package com.example.ewaserver.controllers;
import com.example.ewaserver.exceptions.PreConditionFailed;
import com.example.ewaserver.exceptions.ResourceNotFoundException;
import com.example.ewaserver.models.Lobby;
import com.example.ewaserver.models.User;
import com.example.ewaserver.models.UserHasLobby;
import com.example.ewaserver.notifications.NotificationDistributor;
import com.example.ewaserver.repositories.LobbyRepository;
import com.example.ewaserver.repositories.UserHasLobbyRepository;
import com.example.ewaserver.repositories.UserRepository;
import com.example.ewaserver.rest.LobbyController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class LobbyControllerTest {

    @Mock
    private LobbyRepository lobbyRepository;

    @Mock
    private UserHasLobbyRepository userLobbyRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private NotificationDistributor notificationDistributor;

    @InjectMocks
    private LobbyController lobbyController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllLobbys_ReturnsListOfLobbys() {
        // Arrange
        // Creëer een lijst van verwachte Lobbys
        List<Lobby> expectedLobbys = new ArrayList<>();
        expectedLobbys.add(new Lobby("join_code1", 0, 5, 10, 0, 1));
        expectedLobbys.add(new Lobby("join_code2", 1, 3, 6, 1, 2));
        when(lobbyRepository.findAll()).thenReturn(expectedLobbys);

        // Act
        List<Lobby> actualLobbys = lobbyController.getAllLobbys();

        // Assert
        // Controleer of de daadwerkelijke Lobbys overeenkomen met de verwachte Lobbys
        assertEquals(expectedLobbys, actualLobbys);
    }

    @Test
    void getLobbyOwner_ValidOwnerId_ReturnsListOfLobbys() {
        // Arrange
        int ownerId = 1;
        List<Lobby> expectedLobbys = new ArrayList<>();
        expectedLobbys.add(new Lobby("join_code1", 0, 5, 10, 0, ownerId));
        expectedLobbys.add(new Lobby("join_code2", 1, 3, 6, 1, ownerId));
        when(lobbyRepository.findByQuery("find_lobby_owner_name", ownerId)).thenReturn(expectedLobbys);

        // Act
        List<Lobby> actualLobbys = lobbyController.getLobbyOwner(ownerId);

        // Assert
        assertEquals(expectedLobbys, actualLobbys);

        // Verifieer of de methode `findByQuery("find_lobby_owner_name", ownerId)` van de `lobbyRepository`
        // één keer is aangeroepen met de gegeven argumenten
        verify(lobbyRepository, times(1)).findByQuery("find_lobby_owner_name", ownerId);
    }

    @Test
    void getLobbyOwner_InvalidOwnerId_ReturnsEmptyList() {
        // Arrange
        // Stel een ongeldige ownerId in voor de test
        int ownerId = 5;
        when(lobbyRepository.findByQuery("find_lobby_owner_name", ownerId)).thenReturn(new ArrayList<>());

        // Act
        List<Lobby> actualLobbys = lobbyController.getLobbyOwner(ownerId);

        // Assert
        // Controleer of de daadwerkelijke Lobbys niet null zijn en een lege lijst zijn
        assertNotNull(actualLobbys);
        assertTrue(actualLobbys.isEmpty());
        verify(lobbyRepository, times(1)).findByQuery("find_lobby_owner_name", ownerId);
    }

    @Test
    void deleteLobby_ValidId_ReturnsDeletedLobby() {
        // Arrange
        int lobbyId = 1;
        Lobby existingLobby = new Lobby("join_code1", 0, 5, 10, 0, 1);
        when(lobbyRepository.deleteById(lobbyId)).thenReturn(existingLobby);

        // Act
        Lobby deletedLobby = lobbyController.deletelobby(lobbyId);

        // Assert
        assertEquals(existingLobby, deletedLobby);
    }

    @Test
    void getUserById_ValidId_ReturnsUser() {
        // Arrange
        int userId = 1;
        User user = new User();
        when(userRepository.findById(userId)).thenReturn(user);

        // Act
        User retrievedUser = lobbyController.getUserById(userId);

        // Assert
        assertEquals(user, retrievedUser);
    }

    @Test
    void testGetLobbyByCode_ValidCode_ReturnsListOfLobbys() {
        // Arrange
        String joinCode = "ABC123";
        List<Lobby> expectedLobbys = new ArrayList<>();
        expectedLobbys.add(new Lobby(joinCode, 0, 5, 10, 0, 1));
        when(lobbyRepository.findByQuery("Lobby_find_by_code", joinCode)).thenReturn(expectedLobbys);

        // Act
        List<Lobby> actualLobbys = lobbyController.getLobbyByCode(joinCode);

        // Assert
        assertEquals(expectedLobbys, actualLobbys);
        verify(lobbyRepository, times(1)).findByQuery("Lobby_find_by_code", joinCode);
    }
}