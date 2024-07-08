package com.example.ewaserver.rest;

import com.example.ewaserver.models.User;
import com.example.ewaserver.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class LeaderboardControllerTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private LeaderboardController leaderboardController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(leaderboardController).build();
    }

    @Test
    public void getSummary() throws Exception {

        User user1 = new User("Elvis1", "James", "Appelboom",
                "j.appelboom@live.nl", "secretpassword", 123, "Player");
        User user2 = new User("Elvis2", "Jenkins", "Peerboom",
                "j.peerboom@live.nl", "secretpassword", 240, "Player");
        List<User> userList = Arrays.asList(user1, user2);

        when(userRepository.findAll()).thenReturn(userList);


        mockMvc.perform(get("/leaderboard"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].username").value("Elvis1"))
                .andExpect(jsonPath("$[1].username").value("Elvis2"));

        verify(userRepository, times(1)).findAll();
        verifyNoMoreInteractions(userRepository);
    }
}