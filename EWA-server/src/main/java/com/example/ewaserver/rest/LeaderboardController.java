package com.example.ewaserver.rest;

import com.example.ewaserver.Config;
import com.example.ewaserver.models.User;
import com.example.ewaserver.models.UserHasLobby;
import com.example.ewaserver.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/leaderboard")
public class LeaderboardController {
    @Autowired
    Config apiconfig;
    private final UserRepository userRepository;

    public LeaderboardController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping(path = "", produces = "application/json")
    public List<User> getSummary() {
        return userRepository.findAll();
    }
}
