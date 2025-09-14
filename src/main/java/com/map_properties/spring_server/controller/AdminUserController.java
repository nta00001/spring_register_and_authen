package com.map_properties.spring_server.controller;

import com.map_properties.spring_server.config.role.RequireRoles;
import com.map_properties.spring_server.dto.ListResponseDTO;
import com.map_properties.spring_server.dto.UpdateUserRolesRequest;
import com.map_properties.spring_server.dto.UpdateUserStatusRequest;
import com.map_properties.spring_server.dto.UserWithRolesDTO;
import com.map_properties.spring_server.enums.ERole;
import com.map_properties.spring_server.request.FilterUserRequest;
import com.map_properties.spring_server.service.AdminUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/users")
@CrossOrigin(origins = "*")
public class AdminUserController {

    @Autowired
    private AdminUserService adminUserService;

    @GetMapping("")
    @RequireRoles({ERole.ROLE_ADMIN})
    public ResponseEntity<ListResponseDTO<UserWithRolesDTO>> getAllUsers(@RequestBody(required = false) FilterUserRequest request) {
        return ResponseEntity.ok(adminUserService.getAllUsers(request));
    }

    @PatchMapping("/{userId}/status")
    @RequireRoles({ERole.ROLE_ADMIN})
    public ResponseEntity<UserWithRolesDTO> updateUserStatus(@PathVariable Long userId, @Valid @RequestBody UpdateUserStatusRequest request) {
        return ResponseEntity.ok(adminUserService.updateUserStatus(userId, request));
    }

    @PutMapping("/{userId}/roles")
    @RequireRoles({ERole.ROLE_ADMIN})
    public ResponseEntity<UserWithRolesDTO> updateUserRoles(@PathVariable Long userId, @Valid @RequestBody UpdateUserRolesRequest request) {
        return ResponseEntity.ok(adminUserService.updateUserRoles(userId, request));
    }
}