package com.map_properties.spring_server.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.map_properties.spring_server.dto.AuthResponseDTO;
import com.map_properties.spring_server.dto.UserWithRolesDTO;
import com.map_properties.spring_server.dto.UserWithRolesDetailDTO;
import com.map_properties.spring_server.entity.User;
import com.map_properties.spring_server.exception.LoginException;
import com.map_properties.spring_server.mapper.UserWithRolesMapper;
import com.map_properties.spring_server.repository.UserRepository;
import com.map_properties.spring_server.request.AuthRequest;
import com.map_properties.spring_server.service.JwtService;
import com.map_properties.spring_server.service.AuthenticationService;
import com.map_properties.spring_server.utils.CastUtil;
import com.map_properties.spring_server.utils.UserUtil;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    UserWithRolesMapper userMapper;

    public UserWithRolesDetailDTO getMe() {
        User user = UserUtil.getUser();
        if (user == null) {
            throw new BadCredentialsException("Invalid Credentials");
        }
        UserWithRolesDTO userWithRolesDTO = userMapper.toDTO(user);
        UserWithRolesDetailDTO userDetail = new UserWithRolesDetailDTO(userWithRolesDTO);
        return userDetail;
    }

    public AuthResponseDTO webAuthenticate(AuthRequest authRequest) {
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
            AuthResponseDTO authResponse = CastUtil.convertToClass(response, AuthResponseDTO.class);
            return authResponse;
        } else {
            throw new LoginException("Email or Password is incorrect");
        }
    }
}
