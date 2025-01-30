package com.map_properties.spring_server.controller;

import com.map_properties.spring_server.request.AuthRequest;
import com.map_properties.spring_server.entity.User;
import com.map_properties.spring_server.exception.LoginException;
import com.map_properties.spring_server.service.JwtService;

import jakarta.validation.Valid;

import com.map_properties.spring_server.service.CustomUserDetailsService;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
@CrossOrigin(origins = "*")
public class AuthenticateController {

    @Autowired
    private CustomUserDetailsService service;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome this endpoint is not secure";
    }

    @PostMapping("/add-new-user")
    public String addNewUser(@RequestBody User userInfo) {
        return service.addUser(userInfo);
    }

    @GetMapping("/me")
    public String userProfile() {
        return "Welcome to User Profile";
    }

    @PostMapping("/web-authenticate")
    public ResponseEntity<Object> authenticateAndGetToken(@Valid @RequestBody AuthRequest authRequest)
            throws LoginException {
        Authentication authentication = null;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getEmail(),
                            authRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new LoginException("Invalid credentials");
        }
        if (authentication.isAuthenticated()) {
            Map<String, Object> response = jwtService.generateToken(authRequest.getEmail());
            return ResponseEntity.ok(response);
        } else {
            throw new LoginException("Invalid credentials");
        }
    }
}
