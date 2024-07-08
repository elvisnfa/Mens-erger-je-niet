package com.example.ewaserver.repositories;

import com.example.ewaserver.models.Playerposition;
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
import static org.junit.jupiter.api.Assertions.assertNotEquals;

//Damiën test
@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION,
        classes = {Repository.class, Entity.class}))
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class LudoRepositoryTest {

    @Autowired
    EntityRepository<Playerposition> playerPRepository;

    private List<Playerposition> playerpositionList;

    @BeforeEach
    public void setup() {
        this.playerpositionList = this.playerPRepository.findAll();
    }


    @Test
    public void repoFindAllReturnsAll() {
        // check users have been loaded
        // Has to be updated if the records in the DB are changed.
        assertEquals(58, this.playerpositionList.size());
    }


    @Test
    public void repoFindByIdReturnsUser() {
        // test if object in list will be found by get by id.
        Playerposition playerposition = this.playerpositionList.get(0);
        Playerposition playerposition1 = playerPRepository.findById(playerposition.getIdPlayerposition());

        // Comfirm if it is the same playerPos
        assertEquals(playerposition.getTokenId(), playerposition1.getTokenId());
        assertEquals(playerposition.getOnField(), playerposition1.getOnField());
        assertEquals(playerposition.getTokenPos(), playerposition1.getTokenPos());
    }

    @Test
    public void repoDeletePlayerposition() {
        // test if object in list will be found by get by id.
        Playerposition playerposition = this.playerpositionList.get(0);
        Playerposition playerposition1 = playerPRepository.deleteById(playerposition.getIdPlayerposition());

        //test if the deleted position is the same as the selected position.
        assertEquals(playerposition,playerposition1);

        //replace the playerlist with the new one.
        this.playerpositionList = this.playerPRepository.findAll();

        //test if the first pos of the playerlist is changed.
        assertNotEquals(this.playerpositionList.get(0),playerposition);
        assertEquals(57, this.playerpositionList.size());
    }
}
