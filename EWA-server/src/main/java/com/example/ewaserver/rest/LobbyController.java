package com.example.ewaserver.rest;


import com.example.ewaserver.exceptions.PreConditionFailed;
import com.example.ewaserver.exceptions.ResourceNotFoundException;
import com.example.ewaserver.models.Lobby;
import com.example.ewaserver.models.User;
import com.example.ewaserver.models.UserHasLobby;
import com.example.ewaserver.notifications.NotificationDistributor;
import com.example.ewaserver.repositories.LobbyRepository;
import com.example.ewaserver.repositories.UserHasLobbyRepository;
import com.example.ewaserver.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/Lobby")
public class LobbyController {

    @Autowired
    private LobbyRepository lobbyRepository;

    @Autowired
    private UserHasLobbyRepository userLobbyRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NotificationDistributor notificationDistributor;

    @GetMapping(path = "", produces = "application/json")
    public List<Lobby> getAllLobbys() {
        return lobbyRepository.findAll();
    }

    @GetMapping(path = "/owner/{id}", produces = "application/json")
    public List<Lobby> getLobbyOwner(@PathVariable int id) {
        return lobbyRepository.findByQuery("find_lobby_owner_name", id);
    }

    @GetMapping(path = "/lobby's/{id}", produces = "application/json")
    public List<Lobby> getLobbyForPlayer(@PathVariable int id) {
        return lobbyRepository.findByQuery("Find_all_lobby's_connected_with_player", id);
    }

    @GetMapping(path = "/{join_code}", produces = "application/json")
    public List<Lobby> getLobbyByCode(@PathVariable String join_code) {
        return lobbyRepository.findByQuery("Lobby_find_by_code", join_code);
    }

    @GetMapping(path = "/lobby/{LobbyId}", produces = "application/json")
    public List<UserHasLobby> getUsersConnectedToLobby(@PathVariable int LobbyId) {
        return userLobbyRepository.findByQuery("find_users_by_lobbyid", LobbyId);
    }

    @GetMapping(path = "/color/{LobbyId}", produces = "application/json")
    public List<UserHasLobby> getColorToLobby(@PathVariable int LobbyId) {
        return userLobbyRepository.findByQuery("find_available_color", LobbyId);
    }

    @GetMapping(path = "/{LobbyId}/{userId}", produces = "application/json")
    public List<UserHasLobby> getColorForConnectedUsers(@PathVariable int LobbyId, @PathVariable int userId) {
        return userLobbyRepository.findByQuery("find_color_withLobbyAndUser", LobbyId,userId);
    }

    @GetMapping(path = "/count/{LobbyCode}", produces = "application/json")
    public List<UserHasLobby> getMaxPlayerCountCompare(@PathVariable String LobbyCode) {
        return userLobbyRepository.findByQuery("find_player_count_MAX_Compare", LobbyCode);
    }

    @GetMapping(path = "/maxPlayer/{LobbyCode}", produces = "application/json")
        public List<Lobby> getMaxPlayer(@PathVariable String LobbyCode) {
            return lobbyRepository.findByQuery("total_max_players", LobbyCode);
        }


    @PostMapping(path = "/{userid}/{LobbyId}/{selectedcolor}", produces = "application/json")
    public UserHasLobby CombineLobbyWithUser(
            @PathVariable int userid,
            @PathVariable int LobbyId,
            @PathVariable String selectedcolor) {
        User user = userRepository.findById(userid);
        Lobby lobby = lobbyRepository.findById(LobbyId);
        UserHasLobby userHasLobby = new UserHasLobby();
        userHasLobby.setSelected_color(selectedcolor);

        user.addLobby(userHasLobby);
        lobby.addUser(userHasLobby);

        userLobbyRepository.Save(userHasLobby);

        this.notificationDistributor.notify(Integer.toString(LobbyId));
        return userLobbyRepository.Save(userHasLobby);
    }

    @PostMapping(path = "", produces = "application/json")
    public ResponseEntity<Object> CreateNewLobby(@RequestBody Lobby lobby) {

        Lobby saveLobby = lobbyRepository.Save(lobby);

        URI location = ServletUriComponentsBuilder.
                fromCurrentRequest()
                .path("/{id}").
                buildAndExpand(saveLobby.getIdLobby()).toUri();

        return ResponseEntity.created(location).body(saveLobby);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<Lobby> updateLobby(@RequestBody Lobby lobby, @PathVariable int id) {
        Lobby saveLobby = lobbyRepository.findById(id);
        if (saveLobby.getIdLobby() != id) {
            throw new PreConditionFailed("Id is not equal.");
        }

        saveLobby.setLobby_status(lobby.getLobby_status());
        lobbyRepository.Save(saveLobby);
        return ResponseEntity.ok().body(saveLobby);
    }

    @PutMapping(path = "/player/{id}")
    public ResponseEntity<Lobby> updatePlayerCount(@PathVariable int id) {
        Lobby saveLobby = lobbyRepository.findById(id);
        if (saveLobby.getIdLobby() != id) {
            throw new PreConditionFailed("Id is not equal.");
        }

        saveLobby.setPlayer_size(saveLobby.getPlayer_size() + 1);
        lobbyRepository.Save(saveLobby);
        return ResponseEntity.ok().body(saveLobby);
    }


    @DeleteMapping(path = "/{id}")
    public Lobby deletelobby(@PathVariable() int id) {

        Lobby lobby = this.lobbyRepository.deleteById(id);
        if (lobby == null) {
            throw new ResourceNotFoundException("Cannot delete an lobby with id=" + id);
        }
        return lobby;
    }

    @GetMapping(path = "/get/{id}", produces = "application/json")
    public User getUserById(@PathVariable int id) {

        User user = userRepository.findById(id);
        if (user == null) {
            throw new ResourceNotFoundException("user not found with id: " + id);
        }
        return user;
    }
}