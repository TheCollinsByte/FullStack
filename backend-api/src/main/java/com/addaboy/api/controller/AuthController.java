package com.addaboy.api.controller;

import com.addaboy.api.config.UserAuthProvider;
import com.addaboy.api.dto.CredentialsDto;
import com.addaboy.api.dto.SignUpDto;
import com.addaboy.api.dto.UserDto;
import com.addaboy.api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

/**
 * Authentication Endpoint (Login and Register endpoint)
 */
@RequiredArgsConstructor
@RestController
public class AuthController {

    private final UserService userService;
    private final UserAuthProvider userAuthProvider;

    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody CredentialsDto credentialsDto) {
        UserDto user = userService.login(credentialsDto);

        user.setToken(userAuthProvider.createToken(user.getLogin()));       // Once log-in create a new or Fresh JWT Token

        return ResponseEntity.ok(user);
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody SignUpDto signUpDto) {
        UserDto user = userService.register(signUpDto);
        user.setToken(userAuthProvider.createToken(user.getLogin()));       // return a fresh created JWT

        return ResponseEntity.created(URI.create("/users/" + user.getId()))
                .body(user);
    }

}