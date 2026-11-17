package com.example.memoapi.controller;

import com.example.memoapi.auth.AuthenticationService;
import com.example.memoapi.dto.LoginRequest;
import com.example.memoapi.dto.LoginResponse;
import com.example.memoapi.dto.RegisterRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthenticationService authenticationService;

    public AuthController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login (@RequestBody LoginRequest loginRequest) {
        LoginResponse response = authenticationService.authentication(loginRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<LoginResponse> register (@RequestBody RegisterRequest registerRequest) {
        LoginResponse response = authenticationService.register(registerRequest);
        return ResponseEntity.ok(response);
    }
}
