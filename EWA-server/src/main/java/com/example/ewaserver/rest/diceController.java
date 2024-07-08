package com.example.ewaserver.rest;

import com.example.ewaserver.Config;
import com.example.ewaserver.exceptions.PreConditionFailed;
import com.example.ewaserver.models.Lobby;
import com.example.ewaserver.models.Playerposition;
import com.example.ewaserver.models.Turn;
import com.example.ewaserver.notifications.NotificationDistributor;
import com.example.ewaserver.repositories.LobbyRepository;
import com.example.ewaserver.repositories.TurnRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(path = "/Dice")
public class diceController {

    @Autowired
    Config apiconfig;

    @Autowired
    private TurnRepository repository;
    @Autowired
    private LobbyRepository lobbyRepository;

    @Autowired
    private NotificationDistributor notificationDistributor;

    @GetMapping(path = "", produces = "application/json")
    public List<Turn> getAllPlayermoves() {
        return repository.findAll();
    }


    @GetMapping(path = "/{lobbyId}", produces = "application/json")
    public List<Turn> getPlayermovesOnLobbyid(@PathVariable int lobbyId) {
        Lobby lobby = lobbyRepository.findById(lobbyId);
        if (lobby == null) {
            throw new PreConditionFailed("Need a valid lobby");
        }

        return repository.findByQuery("Find_Turns_based_of_lobbyId", lobbyId);
    }

    @GetMapping(path = "/number/{lobbyId}/{selectedColor}", produces = "application/json")
    public List<Turn> getPlayerMovesOnColorAndID(@PathVariable int lobbyId,
                                                 @PathVariable String selectedColor) {
        Lobby lobby = lobbyRepository.findById(lobbyId);
        if (lobby == null) {
            throw new PreConditionFailed("Need a valid lobby");
        }

        return repository.findByQuery("Find_Turn_based_of_selectedColor_And_lobby", selectedColor, lobbyId);
    }

    @GetMapping(path = "/{lobbyId}/{selectedColor}", produces = "application/json")
    public List<Turn> getAllOfSpecificTurn(@PathVariable int lobbyId,
                                                 @PathVariable String selectedColor) {
        Lobby lobby = lobbyRepository.findById(lobbyId);
        if (lobby == null) {
            throw new PreConditionFailed("Need a valid lobby");
        }

        return repository.findByQuery("Find_Allofturn_based_of_selectedColor_And_lobby", selectedColor, lobbyId);
    }

    @PostMapping(path = "/{lobbyId}/{selectedcolor}/{result}", produces = "application/json")
    public Turn AddNewRecord(@PathVariable int lobbyId,
                                   @PathVariable String selectedcolor,
                                   @PathVariable int result) {
        Lobby lobby = lobbyRepository.findById(lobbyId);
        if (lobby == null) {
            throw new PreConditionFailed("Need a valid lobby");
        }

        repository.removeAllThrewLast(lobbyId);
        Turn turn = new Turn(selectedcolor, lobby, result, 1,true);
        repository.Save(turn);
        this.notificationDistributor.notify("turns" + lobby.getJoin_code());

        return turn;
    }

    @PutMapping(path = "", produces = "application/json")
    public Turn AddExtraStep(@RequestBody Turn turn) {
        Turn saveTurn = repository.findbyTunrpk(turn.getId());

        repository.removeAllThrewLast(saveTurn.getLobby().getIdLobby());
        saveTurn.setThrowCount(turn.getThrowCount());
        saveTurn.setLastThrow(turn.getLastThrow());
        saveTurn.setThrewAsLast(true);
        repository.Save(saveTurn);
        this.notificationDistributor.notify("turns" + saveTurn.getLobby().getJoin_code());
        return turn;
    }


}