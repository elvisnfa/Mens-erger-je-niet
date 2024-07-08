package com.example.ewaserver.models;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;


@Getter
@Setter
@Embeddable
public
class TurnPK implements Serializable {
    private String selectedColor;
    private int lobbyId;

    @Override
    public int hashCode() {
        return Objects.hash(this.selectedColor, this.lobbyId);
    }  // constructors, getters, setters, equals, and hashCode methods

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TurnPK that = (TurnPK) o;
        return this.lobbyId == that.lobbyId &&
                Objects.equals(this.selectedColor, that.selectedColor);
    }

}
