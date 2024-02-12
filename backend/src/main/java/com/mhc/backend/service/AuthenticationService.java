package com.mhc.backend.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mhc.backend.config.JwtService;
import com.mhc.backend.config.TokenType;
import com.mhc.backend.controller.AuthController;
import com.mhc.backend.dto.AuthenticationRequest;
import com.mhc.backend.dto.AuthenticationResponse;
import com.mhc.backend.dto.UserDto;
import com.mhc.backend.model.Token;
import com.mhc.backend.model.User;
import com.mhc.backend.repository.TokenRepository;
import com.mhc.backend.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository repository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    /**
     * Registers a new user based on the provided UserDto.
     *
     * @param request The UserDto object containing the details of the user to be registered.
     * @return ResponseEntity containing the authentication response with access , refresh tokens , role and user company
     *         if the registration is successful, or an error response with internal server error if an exception occurs.
     */
    public ResponseEntity<AuthenticationResponse> register(UserDto request) {
        var user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .companyName(request.getCompanyName())
                .build();
        var savedUser = repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        saveUserToken(savedUser, jwtToken);
        AuthenticationResponse authenticationResponse= AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
        return ResponseEntity.ok(authenticationResponse);
    }

    /**
     * Authenticates a user based on the provided AuthenticationRequest, generating and returning
     * access and refresh tokens with successful authentication.
     *
     * @param request The AuthenticationRequest object containing the user credentials for authentication.
     * @return ResponseEntity containing the authentication response with access , refresh tokens , role and user company
     *         if the authentication is successful, or an error response with internal server error if an exception occurs.
     */
    public ResponseEntity authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        var user = repository.findByUsername(request.getUsername())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        AuthenticationResponse authenticationResponse = AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .role(user.getRole().toString())
                .companyName(user.getCompanyName())
                .build();
        return ResponseEntity.ok(authenticationResponse);
    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        logger.info("token saved successfully.");
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getUserId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String username;
        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        username = jwtService.extractUsername(refreshToken);
        if (username != null) {
            var user = this.repository.findByUsername(username)
                    .orElseThrow();
            if (jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);
                var authResponse = AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }
}
