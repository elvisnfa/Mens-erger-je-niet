package com.example.ewaserver.repositories;

//ELvis test

import com.example.ewaserver.models.Lobby;
import jakarta.persistence.Entity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION,
        classes = {Repository.class, Entity.class}))
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class LobbyRepositoryTest {

    @Autowired
    EntityRepository<Lobby> lobbyEntityRepository;

    private List<Lobby> lobbyList;

    @BeforeEach
    public void setup() { this.lobbyList = this.lobbyEntityRepository.findAll(); }

    @Test
    public void loadContext() {

        assertNotNull(lobbyEntityRepository);
        assertNotNull(lobbyList);
    }

    @Test
    public void returnAllLobbys() {

        assertEquals(19, this.lobbyList.size());
    }

    @Test
    public void addLobby() {

        Lobby lobby = new Lobby("555555", 0, 4, 4, 1, 1);

        lobbyEntityRepository.Save(lobby);

        this.lobbyList = this.lobbyEntityRepository.findAll();

        assertEquals(20, this.lobbyList.size());
    }
}
