package com.example.ewaserver.rest;

import com.example.ewaserver.Config;
import com.example.ewaserver.exceptions.PreConditionFailed;
import com.example.ewaserver.models.Lobby;
import com.example.ewaserver.models.Playerposition;
import com.example.ewaserver.models.Turn;
import com.example.ewaserver.repositories.LobbyRepository;
import com.example.ewaserver.repositories.LudoRepository;
import com.example.ewaserver.repositories.TurnRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(path = "/Ludo")
public class LudoController {

    @Autowired
    Config apiconfig;

    @Autowired
    private LudoRepository repository;

    @Autowired
    private LobbyRepository lobbyRepository;

    @GetMapping(path = "", produces = "application/json")
    public List<Playerposition> getAllPlayermoves() {
        return repository.findAll();
    }

    @GetMapping(path = "/{tokenId}/{lobbyId}", produces = "application/json")
    public List<Playerposition> getPlayermovesOnLobbyid(@PathVariable int tokenId,
                                                        @PathVariable int lobbyId) {

        Lobby lobby = lobbyRepository.findById(lobbyId);

        return repository.findByQuery("Find_Playermoves_based_of_tokenAndLobby", tokenId, lobby);
    }

    @GetMapping(path = "/{lobbyId}", produces = "application/json")
    public List<Playerposition> getPlayermovesOnLobbyid(@PathVariable int lobbyId) {
        Lobby lobby = lobbyRepository.findById(lobbyId);
        if (lobby == null) {
            throw new PreConditionFailed("Need a valid lobby");
        }

        return repository.findByQuery("Find_Playermoves_based_of_lobbyId", lobby);
    }

    @PostMapping(path = "/save/{lobbyId}", produces = "application/json")
    public ResponseEntity<Object> CreateNewPlayermove(@PathVariable int lobbyId,
                                                      @RequestBody Playerposition pPos) {

        Lobby lobby = lobbyRepository.findById(lobbyId);
        pPos.setLobby(lobby);
        Playerposition savePmove = repository.Save(pPos);

        URI location = ServletUriComponentsBuilder.
                fromCurrentRequest()
                .path("/{id}").
                buildAndExpand(savePmove.getIdPlayerposition()).toUri();

        return ResponseEntity.created(location).body(savePmove);

    }

    @PutMapping(path = "")
    public ResponseEntity<Playerposition> updatePlayerMove(@RequestBody Playerposition pos) {
        Playerposition getPos = repository.findById(pos.getIdPlayerposition());
        if (getPos == null) {
            throw new PreConditionFailed("Need a valid playerPos");
        }

        getPos.setTokenPos(pos.getTokenPos());
        getPos.setOnField(pos.getOnField());
        repository.Save(getPos);

        return ResponseEntity.ok().body(getPos);
    }

    @DeleteMapping(path = "", produces = "application/json")
    public Playerposition deletePlayerMove(@RequestBody Playerposition pos) {
        Playerposition getPos = repository.findById(pos.getIdPlayerposition());
        if (getPos == null) {
            throw new PreConditionFailed("Need a valid playerPos");
        }

        return repository.deleteById(getPos.getIdPlayerposition());
    }

}
