package com.example.ewaserver.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@NamedQueries({
        @NamedQuery(name = "Find_Playermoves_based_of_lobbyId",
                query = "select p from Playerposition p where p.lobby = ?1 "),
        @NamedQuery(name = "Find_Playermoves_based_of_tokenAndLobby",
                query = "select p from Playerposition p where p.tokenId = ?1 and p.lobby =?2"),
})
@Entity
@Getter
@Setter
@Table(name = "Playerposition")
public class Playerposition {
    @Id
    @GeneratedValue
    private int idPlayerposition;

    private int tokenId;
    private int tokenPos;

    private int onField;


    @ManyToOne
    private Lobby lobby;

    public Playerposition(int tokenId, int tokenPos, int onField, Lobby lobby) {
        this.tokenId = tokenId;
        this.tokenPos = tokenPos;
        this.onField = onField;
        this.lobby = lobby;
    }

    public Playerposition() {

    }
}
