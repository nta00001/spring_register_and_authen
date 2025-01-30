package com.map_properties.spring_server.controller;

import com.map_properties.spring_server.request.AuthRequest;
import com.map_properties.spring_server.entity.User;
import com.map_properties.spring_server.service.JwtService;

import jakarta.validation.Valid;

import com.map_properties.spring_server.service.CustomUserDetailsService;

import java.util.Map;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

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
    public ResponseEntity<Map<String, Object>> authenticateAndGetToken(@Valid @RequestBody AuthRequest authRequest,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, Object> response = new HashMap<>();
            Map<String, Object> errors = new HashMap<>();

            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.put(error.getField(), new String[] { error.getDefaultMessage() });
            }

            response.put("errors", errors);
            return ResponseEntity.status(422).body(errors);
        }

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));
            if (authentication.isAuthenticated()) {
                Map<String, Object> response = jwtService.generateToken(authRequest.getEmail());
                return ResponseEntity.ok(response);
            } else {
                Map<String, Object> response = new HashMap<>();
                response.put("message", "Invalid credentials");
                return ResponseEntity.status(400).body(response);
            }
        } catch (BadCredentialsException exception) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Invalid credentials");
            return ResponseEntity.status(400).body(response);
        } catch (Exception exception) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", exception.getMessage());
            return ResponseEntity.status(400).body(response);
        }

    }
}
