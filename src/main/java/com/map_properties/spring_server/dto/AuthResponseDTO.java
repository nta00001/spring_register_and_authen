package com.map_properties.spring_server.dto;

import lombok.Getter;

@Getter
public class AuthResponseDTO {
    private String access_token;
    private Integer expires_in;
}
