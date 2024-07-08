package com.example.ewaserver.rest;


import com.example.ewaserver.models.User;
import com.example.ewaserver.repositories.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UserControllerTest2 {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void logout() {
        HttpServletResponse response = mock(HttpServletResponse.class);

        UserController.LogoutResponse logoutResponse = userController.logout(response);
        assertEquals("Succes", logoutResponse.message());
    }

    @Test
    void updateUser() {

        int id = 1;
        User user = new User();
        user.setUserId(id);
        user.setUsername("Elvis");

        when(userRepository.findById(id)).thenReturn(user);
        when(userRepository.Save(user)).thenReturn(user);

        ResponseEntity<User> response = userController.updateUser(user, id);

        assertEquals(user, response.getBody());
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void deleteOwnUser() {

        int id = 1;
        User user = new User();
        user.setUserId(id);
        user.setUsername("Elvis");

        when(userRepository.deleteById(id)).thenReturn(user);

        User deletedUser = userController.deleteOwnUser(id);
        assertEquals(user, deletedUser);
    }

    @Test
    void getUserById() {

        int userId = 1;
        User user = new User();
        user.setUserId(userId);
        user.setUsername("testUser");

        when(userRepository.findById(userId)).thenReturn(user);

        User response = userController.getUserById(userId);
        assertEquals(user, response);
    }

}