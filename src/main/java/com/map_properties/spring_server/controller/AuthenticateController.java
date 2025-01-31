package com.map_properties.spring_server.controller;

import com.map_properties.spring_server.request.AuthRequest;
import com.map_properties.spring_server.response.AuthResponse;
import com.map_properties.spring_server.response.UserDetail;
import com.map_properties.spring_server.exception.LoginException;
import com.map_properties.spring_server.service.UserService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
@CrossOrigin(origins = "*")
public class AuthenticateController {

    @Autowired
    UserService userService;

    @GetMapping("/me")
    public ResponseEntity<UserDetail> getMe() {
        UserDetail userDetail = userService.getMe();
        return ResponseEntity.ok(userDetail);
    }

    @PostMapping("/web-authenticate")
    public ResponseEntity<AuthResponse> webAuthenticate(@Valid @RequestBody AuthRequest authRequest)
            throws LoginException {
        AuthResponse authResponse = userService.webAuthenticate(authRequest);
        return ResponseEntity.ok(authResponse);
    }
}
