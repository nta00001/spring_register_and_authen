package com.map_properties.spring_server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.map_properties.spring_server.dto.ListResponseDTO;
import com.map_properties.spring_server.dto.RoleDTO;
import com.map_properties.spring_server.request.FilterRoleRequest;
import com.map_properties.spring_server.service.RoleService;

import jakarta.annotation.Nullable;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/roles")
@CrossOrigin(origins = "*")
public class RoleController {

    @Autowired
    RoleService roleService;

    @GetMapping("")
    public ResponseEntity<ListResponseDTO<RoleDTO>> index(@Nullable @RequestBody FilterRoleRequest request) {
        ListResponseDTO<RoleDTO> roles = roleService.getRoles(request);
        return ResponseEntity.ok(roles);
    }

}
