package com.map_properties.spring_server.response;

import lombok.Getter;

@Getter
public class AuthResponse {
    private String access_token;
    private Integer expires_in;
}
