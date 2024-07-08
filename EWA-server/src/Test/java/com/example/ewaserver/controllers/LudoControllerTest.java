package com.example.ewaserver.controllers;

import com.example.ewaserver.models.Lobby;
import com.example.ewaserver.models.Playerposition;
import com.example.ewaserver.models.User;
import com.example.ewaserver.repositories.LobbyRepository;
import com.example.ewaserver.repositories.LudoRepository;
import com.example.ewaserver.repositories.UserRepository;
import com.example.ewaserver.rest.LeaderboardController;
import com.example.ewaserver.rest.LudoController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//Damiën
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LudoControllerTest {

    @Mock
    private LudoRepository repo;

    @Mock
    private LobbyRepository lobbyRepository;

    @InjectMocks
    private LudoController controller;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();


    }

    @Test
    public void getSummary() throws Exception {
        Lobby lobby = new Lobby(1, "5025x", 0, 2, 4, 1, 1);
        Playerposition playerposition = new Playerposition(100, 1, 2, lobby);

        List<Playerposition> playerpositions = Arrays.asList(playerposition);
        when(repo.findAll()).thenReturn(playerpositions);

        mockMvc.perform(get("/Ludo"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].tokenId").value(100))
                .andExpect(jsonPath("$[0].tokenPos").value(1))
                .andExpect(jsonPath("$[0].onField").value(2));
        verify(repo, times(1)).findAll();
        verifyNoMoreInteractions(repo);
    }

    @Test
    void deletePlayermove() {
        Lobby lobby = new Lobby(1, "5025x", 0, 2, 4, 1, 1);
        Playerposition playerposition = new Playerposition(100, 1, 2, lobby);
        playerposition.setIdPlayerposition(1);
        when(repo.findById(1)).thenReturn(playerposition);
        when(repo.deleteById(1)).thenReturn(playerposition);

        Playerposition deletePlayerposition = controller.deletePlayerMove(playerposition);
        assertEquals(playerposition, deletePlayerposition);
    }


    //test to check namedQuery Playermoves based of token and lobby
    @Test
    void getPlayermovesOnLobbyidAndToken() throws Exception {
        Lobby lobby = new Lobby(1, "5025x", 0, 2, 4, 1, 1);
        Playerposition playerposition = new Playerposition(100, 1, 2, lobby);
        playerposition.setIdPlayerposition(1);

        List<Playerposition> playerpositions = Arrays.asList(playerposition);

        int posTokenId = playerposition.getTokenId();
        when(lobbyRepository.findById(1)).thenReturn(lobby);
        when(repo.findByQuery("Find_Playermoves_based_of_tokenAndLobby", posTokenId, lobby))
                .thenReturn(playerpositions);

        mockMvc.perform(get("/Ludo/100/1")).andExpect(status().isOk())
                .andExpect(jsonPath("$[0].tokenId").value(100))
                .andExpect(jsonPath("$[0].tokenPos").value(1))
                .andExpect(jsonPath("$[0].onField").value(2));
        ;

        verify(repo, times(1))
                .findByQuery("Find_Playermoves_based_of_tokenAndLobby", posTokenId, lobby);
    }

    //test to check namedQuery Playermoves based of token and lobby
    @Test
    void getPlayermovesOnLobbyid() throws Exception {
        Lobby lobby = new Lobby(1, "5025x", 0, 2, 4, 1, 1);
        Playerposition playerposition = new Playerposition(100, 1, 2, lobby);
        playerposition.setIdPlayerposition(1);

        List<Playerposition> playerpositions = Arrays.asList(playerposition);

        int posTokenId = playerposition.getTokenId();
        when(lobbyRepository.findById(1)).thenReturn(lobby);
        when(repo.findByQuery("Find_Playermoves_based_of_lobbyId", lobby))
                .thenReturn(playerpositions);

        mockMvc.perform(get("/Ludo/1")).andExpect(status().isOk())
                .andExpect(jsonPath("$[0].tokenId").value(100))
                .andExpect(jsonPath("$[0].tokenPos").value(1))
                .andExpect(jsonPath("$[0].onField").value(2));
        ;

        verify(repo, times(1)).findByQuery("Find_Playermoves_based_of_lobbyId", lobby);
    }


}
