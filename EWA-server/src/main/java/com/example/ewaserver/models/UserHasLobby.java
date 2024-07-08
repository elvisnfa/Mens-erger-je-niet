package com.example.ewaserver.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import javax.lang.model.element.Name;
@NamedQueries({
        @NamedQuery(name = "find_users_by_lobbyid",
                query = "select l.user.userId from UserHasLobby l where l.lobby.idLobby = ?1 "),
        @NamedQuery(name = "find_color_withLobbyAndUser",
                query = "select l.selected_color from UserHasLobby l where l.lobby.idLobby = ?1 and l.user.userId = ?2"),
        @NamedQuery(name = "find_lobby_owner_name",
                query = "select u.username from User u left join Lobby l on u.userId = l.userid_owner where l.userid_owner = ?1"),
        @NamedQuery(name = "find_available_color",
                query = "select l.selected_color from UserHasLobby l where l.lobby.idLobby = ?1"),
        @NamedQuery(name = "find_player_count_MAX_Compare",
                query = "select COUNT(uhs.user.userId) from UserHasLobby uhs inner join Lobby l on l.idLobby = uhs.lobby.idLobby where l.join_code = ?1"),

})

@Entity
@Getter
@Setter
@Table(name = "user_has_lobby")
public class UserHasLobby {

    @EmbeddedId
    private UserHasLobbyPK id = new UserHasLobbyPK();

    @JsonIgnore
    @ManyToOne
    @MapsId("lobbyId")
    private Lobby lobby;

    @JsonIgnore
    @ManyToOne
    @MapsId("userId")
    private User user;

    private int userid_owner;
    private String selected_color;

    public UserHasLobby() {
    }
}


