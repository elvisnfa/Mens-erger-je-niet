package com.example.ewaserver.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import javax.lang.model.element.Name;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NamedQueries({
        @NamedQuery(name = "Lobby_find_by_code",
                query = "select l from Lobby l where l.join_code = ?1 "),
        @NamedQuery(name = "total_max_players",
                query = "select l.max_allowed_Players from Lobby l where  l.join_code = ?1"),
        @NamedQuery(name = "Find_all_lobby's_connected_with_player",
                query = "SELECT l FROM Lobby l JOIN l.users u WHERE u.user.userId = ?1 AND l.lobby_status IN (0, 1)")
})

@Entity
@Getter
@Setter
@Table(name = "lobby")
public class Lobby {

    @Id
    @GeneratedValue
    private int idLobby;
    private String join_code;
    private int isPrivateLobby;
    private int player_size;
    private int max_allowed_Players;
    private int lobby_status;

    private int userid_owner;

    @JsonIgnore
    @OneToMany(mappedBy = "lobby")
    private List<UserHasLobby> users = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "lobby")
    private Set<Playerposition> playerpositions;

    @JsonIgnore
    @OneToMany(mappedBy = "lobby")
    private Set<Turn> turns;


    public Lobby(String join_code, int isPrivateLobby, int player_size, int max_allowed_Players,
                 int lobby_status, int userid_owner) {
        this.join_code = join_code;
        this.isPrivateLobby = isPrivateLobby;
        this.player_size = player_size;
        this.max_allowed_Players = max_allowed_Players;
        this.lobby_status = lobby_status;
        this.userid_owner = userid_owner;
    }

    public Lobby(int idLobby, String join_code, int isPrivateLobby, int player_size, int max_allowed_Players,
                 int lobby_status, int userid_owner) {
        this.idLobby = idLobby;
        this.join_code = join_code;
        this.isPrivateLobby = isPrivateLobby;
        this.player_size = player_size;
        this.max_allowed_Players = max_allowed_Players;
        this.lobby_status = lobby_status;
        this.userid_owner = userid_owner;
    }

    protected Lobby() {
    }


    public void addUser(UserHasLobby userHasLobby) {
        if (!getUsers().contains(userHasLobby)) {
            getUsers().add(userHasLobby);
            userHasLobby.setLobby(this);
        }
    }
}