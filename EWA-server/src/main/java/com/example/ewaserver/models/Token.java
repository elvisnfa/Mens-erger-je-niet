package com.example.ewaserver.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "Token")
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String refreshToken;
    private Date expired_at;
    private Date issued_at;

    @ManyToOne
    @JoinColumn(name = "user_user_id")
    private User user;

    public Token() {
    }

    public Token(int id, String refreshToken, Date expired_at, Date issued_at) {
        this.id = id;
        this.refreshToken = refreshToken;
        this.expired_at = expired_at;
        this.issued_at = issued_at;
    }

    public Token(String refreshToken, Date expired_at, Date issued_at, User user) {
        this.refreshToken = refreshToken;
        this.expired_at = expired_at;
        this.issued_at = issued_at;
        this.user = user;
    }
}
