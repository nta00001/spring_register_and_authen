package com.map_properties.spring_server.controller;

import com.map_properties.spring_server.request.AuthRequest;
import com.map_properties.spring_server.dto.AuthResponseDTO;
import com.map_properties.spring_server.dto.UserDetailDTO;
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
    public ResponseEntity<UserDetailDTO> getMe() {
        UserDetailDTO userDetail = userService.getMe();
        return ResponseEntity.ok(userDetail);
    }

    @PostMapping("/web-authenticate")
    public ResponseEntity<AuthResponseDTO> webAuthenticate(@Valid @RequestBody AuthRequest authRequest)
            throws LoginException {
        AuthResponseDTO authResponse = userService.webAuthenticate(authRequest);
        return ResponseEntity.ok(authResponse);
    }
}
