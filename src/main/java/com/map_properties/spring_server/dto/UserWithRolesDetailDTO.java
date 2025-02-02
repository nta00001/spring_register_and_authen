package com.map_properties.spring_server.dto;

import lombok.Getter;

@Getter
public class UserWithRolesDetailDTO {
    private UserWithRolesDTO user;

    public UserWithRolesDetailDTO(UserWithRolesDTO user) {
        this.user = user;
    }
}
