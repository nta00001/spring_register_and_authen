package com.map_properties.spring_server.service;

import com.map_properties.spring_server.dto.AuthResponseDTO;
import com.map_properties.spring_server.dto.UserWithRolesDetailDTO;
import com.map_properties.spring_server.request.AuthRequest;

public interface AuthenticationService {
    UserWithRolesDetailDTO getMe();

    AuthResponseDTO webAuthenticate(AuthRequest authRequest);
}
