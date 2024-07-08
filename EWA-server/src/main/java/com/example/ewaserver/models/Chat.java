package com.example.ewaserver.models;

import com.example.ewaserver.models.User;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private LocalDateTime date;
    private String message;

    @ManyToOne
    @JoinColumn(name = "fromUser", referencedColumnName = "userId")
    private User fromUser;

    @ManyToOne
    @JoinColumn(name = "toUser", referencedColumnName = "userId")
    private User toUser;


    public Chat() {
    }

    public Chat(int id, LocalDateTime date) {
        this.id = id;
        this.date = date;
    }

    public Chat(LocalDateTime date, String message) {
        this.date = date;
        this.message = message;
    }

    public void setFromUser(User fromUser) {
        this.fromUser = fromUser;
    }

    public void setToUser(User toUser) {
        this.toUser = toUser;
    }
}
