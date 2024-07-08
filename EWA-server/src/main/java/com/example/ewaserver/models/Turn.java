package com.example.ewaserver.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@NamedQueries({
        @NamedQuery(name = "Find_Turns_based_of_lobbyId",
                query = "select p from Turn p where p.lobby.idLobby = ?1 "),
        @NamedQuery(name = "Find_Turn_based_of_selectedColor_And_lobby",
                query = "select p.lastThrow from Turn p where p.id.selectedColor = ?1 and p.lobby.idLobby =?2"),
        @NamedQuery(name = "Find_Allofturn_based_of_selectedColor_And_lobby",
                query = "select p from Turn p where p.id.selectedColor = ?1 and p.lobby.idLobby =?2"),
})

@Entity
@Getter
@Setter
@Table(name = "Turn")
public class Turn {
    @EmbeddedId
    private TurnPK id = new TurnPK();

    @ManyToOne
    @MapsId("lobbyId")
    private Lobby lobby;

    private int lastThrow;
    private int throwCount;
    private boolean threwAsLast;

    public Turn(String selectedColor, Lobby lobby, int lastThrow, int throwCount) {
        this.id.setSelectedColor(selectedColor);
        this.lobby = lobby;
        this.lastThrow = lastThrow;
        this.throwCount = throwCount;
    }

    public Turn(String selectedColor, Lobby lobby, int lastThrow, int throwCount, boolean threwAsLast) {
        this.id.setSelectedColor(selectedColor);
        this.lobby = lobby;
        this.lastThrow = lastThrow;
        this.throwCount = throwCount;
        this.threwAsLast = threwAsLast;
    }

    public Turn() {

    }
}
