package com.map_properties.spring_server.service;

import com.map_properties.spring_server.dto.AuthResponseDTO;
import com.map_properties.spring_server.dto.UserDTO;
import com.map_properties.spring_server.dto.UserWithRolesDetailDTO;
import com.map_properties.spring_server.request.AuthRequest;
import com.map_properties.spring_server.request.RegisterRequest;

public interface AuthenticationService {
    UserWithRolesDetailDTO getMe();
    UserDTO register(RegisterRequest registerRequest);
    AuthResponseDTO webAuthenticate(AuthRequest authRequest);
}
