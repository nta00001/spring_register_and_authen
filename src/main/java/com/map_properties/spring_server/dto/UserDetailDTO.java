package com.map_properties.spring_server.dto;

import lombok.Getter;

@Getter
public class UserDetailDTO {
    private UserDTO user;

    public UserDetailDTO(UserDTO user) {
        this.user = user;
    }
}
