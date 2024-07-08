package com.example.ewaserver.rest;


import com.example.ewaserver.Config;
import com.example.ewaserver.exceptions.PreConditionFailed;
import com.example.ewaserver.exceptions.ResourceNotFoundException;
import com.example.ewaserver.exceptions.UnAuthorizedException;
import com.example.ewaserver.models.User;
import com.example.ewaserver.repositories.UserRepository;

import com.example.ewaserver.service.JWToken;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(path = "/users")
public class UserController {


    @Autowired
    Config apiconfig;

    @Autowired
    private UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @GetMapping(path = "", produces = "application/json")
    public List<User> getSummary() {
        return userRepository.findAll();
    }


    record UserResponse(
            @JsonProperty("username") String username,
            @JsonProperty("firstname") String firstname,
            @JsonProperty("lastname") String lastname,
            String email) {
    }

    @GetMapping(value = "/info")
    public ResponseEntity<User> userInfo(HttpServletRequest request){
        String token = request.getHeader("Authorization").replace("Bearer ", "");
        JWToken decoded = JWToken.decode(token,apiconfig.getIssuer(),
                apiconfig.getPassphrase());

        User user = this.userRepository.findById((int) decoded.getAccountId());
        if (user == null){
            throw new ResourceNotFoundException("Cannot find an account related to " + decoded.getAccountId());
        }

        return ResponseEntity.ok().body(user);
    }


    @GetMapping(value = "/token")
    public String token(HttpServletRequest request) {
        return request.getHeader("Authorization").replace("Bearer ", "");
    }


    record LogoutResponse(String message) {
    }

    @PostMapping(value = "/logout")
    public LogoutResponse logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("refresh_token", null);
        cookie.setMaxAge(0);
        cookie.setHttpOnly(true);

        response.addCookie(cookie);

        return new LogoutResponse("Succes");
    }



    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@RequestBody User user,@PathVariable int id) {
        User saveUser = userRepository.findById(id);
        if (saveUser.getUserId() != id) {
            throw new PreConditionFailed("Id is not equal.");
        }

        if (user == null) {
            throw new PreConditionFailed("Need a user");
        }

        saveUser.setEmail(user.getEmail());
        saveUser.setUsername(user.getUsername());
        saveUser.setFirstname(user.getFirstname());
        saveUser.setLastname(user.getLastname());
        saveUser.setPoints(user.getPoints());

        userRepository.Save(saveUser);
        return ResponseEntity.ok().body(saveUser);
    }

    @PutMapping("/points")
    public ResponseEntity<User> updateUserPoints(@RequestBody User user) {
        User saveUser = userRepository.findById(user.getUserId());
        if (user == null) {
            throw new PreConditionFailed("Need a user");
        }

        saveUser.setPoints(user.getPoints());
        userRepository.Save(saveUser);
        return ResponseEntity.ok().body(saveUser);
    }

//    @DeleteMapping(path = "/{id}")
//    public User deleteUser(@PathVariable() int id,
//                           @RequestAttribute(name = JWToken.JWT_ATTRIBUTE_NAME) JWToken jwtInfo) {
//        if (jwtInfo == null || !jwtInfo.isAdmin()) {
//            throw new UnAuthorizedException(
//                    "Admin role is required to remove an user");
//        }
//        User user = this.userRepository.deleteById(id);
//        if (user == null) {
//            throw new ResourceNotFoundException(
//                    "Cannot delete an user with id=" + id);
//        }
//        return user;
//    }

    @DeleteMapping(path = "/{id}")
    public User deleteOwnUser(@PathVariable() int id) {

        User user = this.userRepository.deleteById(id);
        if (user == null) {
            throw new ResourceNotFoundException(
                    "Cannot delete an user with id=" + id);
        }

        return user;
    }

    @GetMapping(path = "/get/{id}", produces = "application/json")
    public User getUserById(@PathVariable int id) {

        User user = userRepository.findById(id);
        if (user == null) {
            throw new ResourceNotFoundException("user not found with id: " + id);
        }
        return user;
    }

    @GetMapping(path = "/count")
    public int getAmountOfUsers() {
        return userRepository.getAmounOfUsers();
    }

}
