package com.example.tickr.tickr.controller;

import com.example.tickr.tickr.TickrResponse;
import com.example.tickr.tickr.common.request.AuthRequest;
import com.example.tickr.tickr.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:8081")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<TickrResponse> register(@RequestBody AuthRequest request) {
        return new ResponseEntity<>(
            new TickrResponse("User registered successfully", authService.register(request)),
            HttpStatus.CREATED
        );
    }

    @PostMapping("/login")
    public ResponseEntity<TickrResponse> login(@RequestBody AuthRequest request) {
        return new ResponseEntity<>(
            new TickrResponse("User Logged In", authService.login(request)),
        HttpStatus.OK);
    }
}
