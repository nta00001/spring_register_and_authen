package com.map_properties.spring_server.service;

import com.map_properties.spring_server.dto.AuthResponseDTO;
import com.map_properties.spring_server.dto.UserDetailDTO;
import com.map_properties.spring_server.request.AuthRequest;

public interface UserService {
    UserDetailDTO getMe();

    AuthResponseDTO webAuthenticate(AuthRequest authRequest);
}
