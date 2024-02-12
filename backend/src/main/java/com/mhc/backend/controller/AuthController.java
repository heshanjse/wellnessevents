package com.mhc.backend.controller;

import com.mhc.backend.dto.AuthenticationRequest;
import com.mhc.backend.dto.AuthenticationResponse;
import com.mhc.backend.dto.UserDto;
import com.mhc.backend.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationService authenticationService;

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest authenticationRequest) {
        return authenticationService.authenticate(authenticationRequest);
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody UserDto user) {
        return authenticationService.register(user);
    }
}
