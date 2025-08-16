package com.map_properties.spring_server.controller;

import com.map_properties.spring_server.request.AuthRequest;
import com.map_properties.spring_server.dto.AuthResponseDTO;
import com.map_properties.spring_server.dto.UserWithRolesDetailDTO;

import com.map_properties.spring_server.service.AuthenticationService;
import com.map_properties.spring_server.request.RegisterRequest;
import com.map_properties.spring_server.dto.UserDTO;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
@CrossOrigin(origins = "*")
public class AuthenticateController {

    @Autowired
    AuthenticationService authService;

    @GetMapping("/me")
    public ResponseEntity<UserWithRolesDetailDTO> getMe() {
        UserWithRolesDetailDTO userDetail = authService.getMe();
        return ResponseEntity.ok(userDetail);
    }

    @PostMapping("/web-authenticate")
    public ResponseEntity<AuthResponseDTO> webAuthenticate(@Valid @RequestBody AuthRequest authRequest) {
        AuthResponseDTO authResponse = authService.webAuthenticate(authRequest);
        return ResponseEntity.ok(authResponse);
    }

    @PostMapping("/register") //
    public ResponseEntity<UserDTO> register(@Valid @RequestBody RegisterRequest registerRequest) {
        UserDTO newUser = authService.register(registerRequest);
        return ResponseEntity.ok(newUser);
    }
}
