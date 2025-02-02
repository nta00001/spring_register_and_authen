package com.map_properties.spring_server.dto;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class UserWithRolesDTO {
    private String uuid;
    private String email;
    private String avatar_url;
    private Set<RoleDTO> roles;
}
