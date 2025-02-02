package com.map_properties.spring_server.dto;

import java.util.Set;
import java.util.stream.Collectors;

import com.map_properties.spring_server.entity.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserWithRolesDTO {
    private String uuid;
    private String email;
    private String avatar_url;
    private Set<RoleDTO> roles;

    public UserWithRolesDTO(User user) {
        this.uuid = user.getUuid().toString();
        this.email = user.getEmail();
        this.avatar_url = user.getAvatarUrl();
        this.roles = user.getRoles().stream().map(role -> new RoleDTO(role)).collect(Collectors.toSet());
    }
}
