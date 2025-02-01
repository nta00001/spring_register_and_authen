package com.map_properties.spring_server.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
    private String uuid;
    private String email;
    private String avatarUrl;
}
