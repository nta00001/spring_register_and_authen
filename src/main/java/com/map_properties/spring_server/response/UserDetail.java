package com.map_properties.spring_server.response;

import com.map_properties.spring_server.dto.UserDTO;

import lombok.Getter;

@Getter
public class UserDetail {
    private UserDTO user;

    public UserDetail(UserDTO user) {
        this.user = user;
    }
}
