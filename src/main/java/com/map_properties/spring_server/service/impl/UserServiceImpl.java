package com.map_properties.spring_server.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.map_properties.spring_server.dto.UserDTO;
import com.map_properties.spring_server.entity.User;
import com.map_properties.spring_server.exception.LoginException;
import com.map_properties.spring_server.request.AuthRequest;
import com.map_properties.spring_server.response.AuthResponse;
import com.map_properties.spring_server.response.UserDetail;
import com.map_properties.spring_server.service.JwtService;
import com.map_properties.spring_server.service.UserService;
import com.map_properties.spring_server.utils.CastUtil;
import com.map_properties.spring_server.utils.UserUtil;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    public UserDetail getMe() {
        User user = UserUtil.getUser();
        UserDTO userDTO = modelMapper.map(user, UserDTO.class);
        UserDetail userDetail = new UserDetail(userDTO);
        return userDetail;
    }

    public AuthResponse webAuthenticate(AuthRequest authRequest) throws LoginException {
        Authentication authentication = null;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getEmail(),
                            authRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new LoginException("Email or Password is incorrect");
        }
        if (authentication.isAuthenticated()) {
            Object response = jwtService.generateToken(authRequest.getEmail());
            AuthResponse authResponse = CastUtil.convertToClass(response, AuthResponse.class);
            return authResponse;
        } else {
            throw new LoginException("Email or Password is incorrect");
        }
    }
}
