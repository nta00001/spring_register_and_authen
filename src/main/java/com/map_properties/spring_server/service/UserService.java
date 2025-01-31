package com.map_properties.spring_server.service;

import com.map_properties.spring_server.exception.LoginException;
import com.map_properties.spring_server.request.AuthRequest;
import com.map_properties.spring_server.response.AuthResponse;
import com.map_properties.spring_server.response.UserDetail;

public interface UserService {
    UserDetail getMe();

    AuthResponse webAuthenticate(AuthRequest authRequest) throws LoginException;
}
