package com.example.ewaserver.rest;

import com.example.ewaserver.Config;
import com.example.ewaserver.exceptions.ResourceNotFoundException;
import com.example.ewaserver.exceptions.UnAuthorizedException;
import com.example.ewaserver.models.Token;
import com.example.ewaserver.models.User;
import com.example.ewaserver.repositories.TokenRepository;
import com.example.ewaserver.repositories.UserRepository;
import com.example.ewaserver.service.JWToken;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;


@RestController
@RequestMapping("/authentication")
public class AuthenticationController {
    @Autowired
    Config apiconfig;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


    public AuthenticationController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    //find all method is in this controller so it allows us to debug. Will be removed later on.
    @GetMapping(path = "", produces = "application/json")
    public List<User> getSummary() {
        return userRepository.findAll();
    }


    @PostMapping(path = "/register", produces = "application/json")
    public ResponseEntity<Object> registerUser(@RequestBody User user) {


        // Encode the password before saving
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        User savedUser = userRepository.Save(user);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().
                path("/{id}").buildAndExpand(savedUser.getUserId()).toUri();

        return ResponseEntity.created(location).body(savedUser);
    }

    @PostMapping(value = "/login", produces = "application/json")
    public ResponseEntity<User> login(@RequestBody ObjectNode signInInfo, HttpServletResponse response) {
        String username = signInInfo.get("username").asText();
        String password = signInInfo.get("password").asText();

        User user = userRepository.findByUsername(username);

        if (user == null || !passwordEncoder.matches(password, user.getPassword())) {
            throw new ResourceNotFoundException("Cannot find an account related to " + username);
        }

        // Acces token
        JWToken jwToken = new JWToken(user.getUsername(), (long) user.getUserId(), user.getRole());
        String tokenString = jwToken.encode(this.apiconfig.getIssuer(), this.apiconfig.getPassphrase(),
                this.apiconfig.getTokenDurationOfValidity());

        // Refresh token
        JWToken jwTokenRefresh = new JWToken(user.getUsername(), (long) user.getUserId(), user.getRole());
        String refreshTokenString = jwTokenRefresh.encode(this.apiconfig.getIssuer(),
                this.apiconfig.getRefreshPassphrase(),
                this.apiconfig.getRefreshTokenDurationOfValidity());

        // Add the token to the database with the id of the user.
        Token token = new Token(refreshTokenString, jwTokenRefresh.getExpired_at(), jwTokenRefresh.getIssued_at(), user);
        this.tokenRepository.Save(token);

        // Set the cookie for the refresh Token that is automatically set when logging in.
        Cookie cookie = new Cookie("Refresh_token", refreshTokenString);
        cookie.setMaxAge(36000);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        response.addCookie(cookie);

        // Set the Access-Control-Allow-Credentials header to true
        response.setHeader("Access-Control-Allow-Credentials", "true");


        return ResponseEntity.accepted()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenString)
                .body(user);

    }

    @PostMapping(value = "/refresh", produces = "application/json")
    public ResponseEntity<String> refresh(@CookieValue("Refresh_token") String refreshTokenString) {
        // Decode the refreshToken with the string and passPhrase.
        JWToken refreshToken = JWToken.decode(refreshTokenString, apiconfig.getIssuer(),
                apiconfig.getRefreshPassphrase());
        System.out.println(refreshTokenString);

        User user = this.tokenRepository.
                findByRefreshToken(refreshToken.getAccountId(),
                        refreshTokenString, refreshToken.getExpired_at());

        if (user == null) {
            throw new UnAuthorizedException("User could not be found or refresh_token is invalid");
        }

        // Initialize a new accesToken with the claims that were retrieved from refreshToken.
        JWToken accesToken = new JWToken(
                refreshToken.getCallName(), refreshToken.getAccountId(), refreshToken.getRole()
        );

        // Encode the instance to get the accesTokenString out of it.
        String accesTokenString = accesToken.encode(apiconfig.getIssuer(), apiconfig.getPassphrase(),
                apiconfig.getTokenDurationOfValidity());

        // Return the new accesToken
        return ResponseEntity.accepted()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accesTokenString)
                .body(accesTokenString);

    }

    @PostMapping(value = "/logout/{id}")
    public String logout(@PathVariable int id, HttpServletResponse response) {
        this.tokenRepository.deleteById(id);
        // Delete the refresh token cookie
        Cookie refreshTokenCookie = new Cookie("Refresh_token", "");
        refreshTokenCookie.setMaxAge(0);
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setPath("/");
        response.addCookie(refreshTokenCookie);

        // Set the Access-Control-Allow-Credentials header to true
        response.setHeader("Access-Control-Allow-Credentials", "true");

        return "Logged out and removed tokens";
    }
}
