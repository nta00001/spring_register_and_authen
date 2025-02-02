package com.map_properties.spring_server.dto;

import com.map_properties.spring_server.entity.Role;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleDTO {
    private String name;
    private String code;

    public RoleDTO(Role role) {
        this.name = role.getName();
        this.code = role.getCode();
    }
}
