package com.example.ewaserver.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserTest {

    User user1, user2;


    @BeforeEach
    void setup() {
        this.user1 = new User(1, "IbrahimTest", "ibrahim", "ghzawi", "ibrahim@hva.nl", "",0 , "admin");
        this.user2 = new User(2, "MaartenTest", "maarten", "groen", "maarten@hva.nl", "",10, "admin");
    }


    @Test
    void userConstructorWorksProperly(){
        assertEquals(user1.getFirstname(), "ibrahim");
        assertEquals(user1.getUserId(), 1);
        assertEquals(user2.getUserId(),  2);
        assertEquals(user2.getFirstname(), "maarten");
    }
}
