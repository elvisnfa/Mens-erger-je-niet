package com.example.ewaserver.rest;


import com.example.ewaserver.models.Playerposition;
import com.example.ewaserver.models.User;
import com.example.ewaserver.repositories.UserRepository;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

//Voor Ibrahim

@ExtendWith(SpringExtension.class)
@SpringBootTest
class UserControllerTest {

    @Mock
    private UserRepository userRepository;

    @MockBean
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationController authenticationController;

    @InjectMocks
    private UserController userController;

    private List<User> userList;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.userList = userRepository.findAll();
    }


    @Test
    public void AddUserByAdmin() {

        User user = new User("ibrahimTest", "test", "test", "test", "test@email.com",  0, null);
        ResponseEntity<Object> response =
                this.authenticationController.registerUser(user);

        // check status code, location header and response body of post request
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void userTypeTest() throws Exception {
        for (User entity : userRepository.findAll()) {
            assertNotNull(entity.getRole());
            String userType = entity.getRole();
            switch (userType) {
                case "Player" -> assertEquals("player", entity.getRole());
                case "Admin" -> assertEquals("admin", entity.getRole());
                default -> throw new Exception("USERTYPE IS NOT ALLOWED");
            }
        }
    }

    @Test
    void getSummary() {
        for (User entity : userController.getSummary()) {
            assertInstanceOf(User.class, entity);
        }
    }

    @Test
    void getAmountOfUsersShouldReturnNumberOfUsers() {
        int numberOfUsers = userController.getAmountOfUsers();
        assertTrue(numberOfUsers >= 0);
    }


}