package com.example.tickr.tickr.controller;

import com.example.tickr.tickr.TickrResponse;
import com.example.tickr.tickr.common.utils.RequestUtils;
import com.example.tickr.tickr.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final AuthService authService;

    @GetMapping("/")
    public ResponseEntity<TickrResponse> getUserProfile() {
        UUID userId = RequestUtils.getUserIdFromRequest();
        return new ResponseEntity<>(
            new TickrResponse("User Profile", authService.getUserById(userId)),
            HttpStatus.OK);
    }
}
