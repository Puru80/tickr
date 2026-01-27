package com.example.tickr.tickr.controller;

import com.example.tickr.tickr.TickrResponse;
import com.example.tickr.tickr.common.request.AuthRequest;
import com.example.tickr.tickr.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
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

    @PostMapping("/logout")
    public ResponseEntity<TickrResponse> logout(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String jwt = authHeader.substring(7);
            authService.logout(jwt);
        }
        return new ResponseEntity<>(
            new TickrResponse("User logged out successfully", null),
            HttpStatus.OK
        );
    }
}
