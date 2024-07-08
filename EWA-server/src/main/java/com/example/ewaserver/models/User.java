package com.example.ewaserver.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
//
//@NamedQueries({
//        @NamedQuery(name = "find_user",
//                query = "select u from User u where username = ?1"),
//})

@Entity
@Getter
@Setter
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;

    @JsonProperty("username")
    private String username;

    @JsonProperty("firstname")
    private String firstname;

    @JsonProperty("lastname")
    private String lastname;

    private String email;
    private String password;
    private int points;
    private String role = "Player";

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonBackReference
    @JsonIgnore
    private Set<Token> tokens;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<UserHasLobby> lobbys = new ArrayList<>();

    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "user_has_friend",
            joinColumns =
            @JoinColumn(name = "FRIEND1", referencedColumnName = "userId"),
            inverseJoinColumns =
            @JoinColumn(name = "FRIEND2", referencedColumnName = "userId"))
    Set<User> friends = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "fromUser")
    private List<Chat> sentChats;

    @JsonIgnore
    @OneToMany(mappedBy = "toUser")
    private List<Chat> receivedChats;


    public User(int userId, String username, String firstname, String lastname, String email, String password,
                int points, String role) {
        this.userId = userId;
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.points = points;
        this.role = role;
    }

    public User(String username, String firstname, String lastname, String email, String password,
                int points, String role) {
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.points = points;
        this.role = role;
    }

    public User() {
    }

    public boolean verifyPassword(String GivenPassword) {
        return this.password.equals(GivenPassword);
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                '}';
    }

    public void addLobby(UserHasLobby userHasLobby) {
        if (!getLobbys().contains(userHasLobby)) {
            getLobbys().add(userHasLobby);
            userHasLobby.setUser(this);
        }
    }

    public boolean addFriend(User friend) {
        this.friends.add(friend);
        friend.getFriends().add(this);
        return true;
    }

    public void addToken(Token token) {
        this.tokens.add(token);
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
