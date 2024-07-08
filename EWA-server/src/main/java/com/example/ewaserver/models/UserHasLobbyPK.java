package com.example.ewaserver.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class UserHasLobbyPK implements Serializable {

    private int lobbyId;
    private int userId;

    @Override
    public int hashCode() {
        return Objects.hash(lobbyId, userId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserHasLobbyPK that = (UserHasLobbyPK) o;
        return lobbyId == that.lobbyId &&
                userId == that.userId;
    }
}
