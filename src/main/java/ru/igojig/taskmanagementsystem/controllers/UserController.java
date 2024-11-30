package ru.igojig.taskmanagementsystem.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.igojig.taskmanagementsystem.dto.LoginRequest;
import ru.igojig.taskmanagementsystem.dto.UserDto;
import ru.igojig.taskmanagementsystem.dto.TokenResponse;
import ru.igojig.taskmanagementsystem.services.AuthService;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final AuthService authService;

    @GetMapping("/admin")
    public String admin(Principal principal) {
        return "admin " + principal.getName();
    }

    @GetMapping("/user")
    public String user(Principal principal) {
        return "user " + principal.getName();
    }

    @GetMapping("/authenticated")
    public String authenticated(Principal principal) {
        return "authenticated " + principal.getName();
    }

    @GetMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest loginRequest) {
        String token = authService.login(loginRequest);

        TokenResponse tokenResponse = TokenResponse.builder()
                .message("Login successfully " + loginRequest.getEmail())
                .token(token)
                .build();

        return ResponseEntity.ok(tokenResponse);
    }

    @GetMapping("/register")
    public ResponseEntity<TokenResponse> register(@RequestBody UserDto userDto) {
        String token = authService.register(userDto);
        TokenResponse tokenResponse = TokenResponse.builder()
                .message("User " + userDto.getEmail() + " registered")
                .token(token)
                .build();
        return new ResponseEntity<>(tokenResponse, HttpStatus.CREATED);
    }

}
