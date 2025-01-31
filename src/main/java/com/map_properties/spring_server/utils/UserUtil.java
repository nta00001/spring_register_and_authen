package com.map_properties.spring_server.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.map_properties.spring_server.entity.User;

public class UserUtil {
    public static User getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Check if the authentication is not null and the principal is not anonymous
        if (authentication != null && authentication.getPrincipal() instanceof User) {
            return (User) authentication.getPrincipal(); // Cast to User
        }

        return null; // Or handle anonymous user case as needed
    }
}
