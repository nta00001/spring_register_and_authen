package com.map_properties.spring_server.service.impl;

import com.map_properties.spring_server.dto.UserDTO;
import com.map_properties.spring_server.entity.Role;
import com.map_properties.spring_server.enums.ERole;
import com.map_properties.spring_server.mapper.UserMapper; // SỬA 1: Import đúng UserMapper
import com.map_properties.spring_server.repository.RoleRepository;
import com.map_properties.spring_server.request.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder; // THÊM: Import PasswordEncoder
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

import java.util.Set;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserWithRolesMapper userWithRolesMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserWithRolesDetailDTO getMe() {
        User user = UserUtil.getUser();
        if (user == null) {
            throw new BadCredentialsException("Invalid Credentials");
        }
        UserWithRolesDTO userWithRolesDTO = userWithRolesMapper.toDTO(user);
        UserWithRolesDetailDTO userDetail = new UserWithRolesDetailDTO(userWithRolesDTO);
        return userDetail;
    }

    @Override
    public UserDTO register(RegisterRequest registerRequest) {
        if(userRepository.findByEmail(registerRequest.getEmail()) != null) {
            throw new LoginException("Email đã tồn tại");
        }
        User user = new User();
        user.setName(registerRequest.getName());
        user.setEmail(registerRequest.getEmail());

        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));


        Role userRole = roleRepository.findByCode(ERole.ROLE_USER.getCode());
        if (userRole != null) {
            user.getRoles().add(userRole);
        }

        User savedUser = userRepository.save(user);


        return userMapper.toDTO(savedUser);
    }

    public AuthResponseDTO webAuthenticate(AuthRequest authRequest) {
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getEmail(),
                            authRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new LoginException("Email or Password is incorrect");
        }
        if (authentication.isAuthenticated()) {
            Object response = jwtService.generateToken(authRequest.getEmail());
            return CastUtil.convertToClass(response, AuthResponseDTO.class);
        } else {
            throw new LoginException("Email or Password is incorrect");
        }
    }
}