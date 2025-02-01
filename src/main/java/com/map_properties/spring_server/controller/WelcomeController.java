package com.map_properties.spring_server.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.map_properties.spring_server.config.permission.Roles;
import com.map_properties.spring_server.enums.ERole;

@RestController
@RequestMapping("/")
@CrossOrigin(origins = "*")
public class WelcomeController {
    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome this endpoint is not secure";
    }

    // @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @Roles({ ERole.ROLE_ADMIN })
    @GetMapping("/admin/welcome")
    public String welcomeAdmin() {
        return "Welcome this endpoint is for admin";
    }

    // @PreAuthorize("hasAuthority('ROLE_USER')")
    @Roles({ ERole.ROLE_USER })
    @GetMapping("/user/welcome")
    public String welcomeUser() {
        return "Welcome this endpoint is for user";
    }
}
