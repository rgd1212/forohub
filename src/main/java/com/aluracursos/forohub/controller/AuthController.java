package com.aluracursos.forohub.controller;

import com.alura.ForoHub_challenge_back_end.security.authentication.JwtAuthenticationResponse;
import com.alura.ForoHub_challenge_back_end.security.authentication.LoginRequest;
import com.alura.ForoHub_challenge_back_end.security.login.JwtTokenProvider;
import com.alura.ForoHub_challenge_back_end.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()));

            String token = tokenProvider.generateToken(authentication.getName());
            return ResponseEntity.ok(new JwtAuthenticationResponse(token));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }
}
