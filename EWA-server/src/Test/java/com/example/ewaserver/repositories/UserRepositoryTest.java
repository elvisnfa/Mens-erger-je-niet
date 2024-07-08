package com.example.ewaserver.repositories;

import com.example.ewaserver.models.User;
import jakarta.persistence.Entity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION,
        classes = { Repository.class, Entity.class}))
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {



    @Autowired
    EntityRepository<User> userRespository;

    private List<User> userList;

    @BeforeEach
    public void setup()  {
        this.userList = this.userRespository.findAll();
    }

    @Test
    public void repoFindAllReturnsAll() {
        // check users have been loaded
        assertEquals(42, this.userList.size());
    }

    @Test
    public void repoFindByIdReturnsUser() {
        // Set up the mock to return expected user objects
        for (int i = 0; i < this.userList.size(); i++) {
            User user = this.userList.get(i);
            Mockito.when(userRespository.findById(user.getUserId())).thenReturn(user);
        }

        // Check all users can be found by ID
        for (int i = 0; i < this.userList.size(); i++) {
            User user = userRespository.findById(this.userList.get(i).getUserId());
            assertEquals(this.userList.get(i).getFirstname(), user.getFirstname());
            assertEquals(this.userList.get(i).getLastname(), user.getLastname());
        }

        // Check non-existing ID
        User user = userRespository.findById(20);
        assertNull(user);
    }
}
