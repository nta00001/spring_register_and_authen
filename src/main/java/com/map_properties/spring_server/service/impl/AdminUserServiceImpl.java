package com.map_properties.spring_server.service.impl;

import com.map_properties.spring_server.dto.ListResponseDTO;
import com.map_properties.spring_server.dto.UpdateUserRolesRequest;
import com.map_properties.spring_server.dto.UpdateUserStatusRequest;
import com.map_properties.spring_server.dto.UserWithRolesDTO;
import com.map_properties.spring_server.entity.Role;
import com.map_properties.spring_server.entity.User;
import com.map_properties.spring_server.exception.ResourceNotFoundException;
import com.map_properties.spring_server.mapper.ListResponseMapper;
import com.map_properties.spring_server.mapper.UserWithRolesMapper;
import com.map_properties.spring_server.repository.RoleRepository;
import com.map_properties.spring_server.repository.UserRepository;
import com.map_properties.spring_server.request.FilterUserRequest;
import com.map_properties.spring_server.service.AdminUserService;
import com.map_properties.spring_server.specification.UserSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class AdminUserServiceImpl implements AdminUserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserWithRolesMapper userWithRolesMapper;
    @Autowired
    private ListResponseMapper<UserWithRolesDTO> listResponseMapper;

    @Override
    public ListResponseDTO<UserWithRolesDTO> getAllUsers(FilterUserRequest request) {
        Specification<User> spec = Specification.where(UserSpecification.filterByName(request.getName()))
                .and(UserSpecification.filterByEmail(request.getEmail()));

        Page<UserWithRolesDTO> pageUsers = userRepository.findAll(spec, request.toPageable())
                .map(user -> userWithRolesMapper.toDTO(user));

        return listResponseMapper.toDTO(pageUsers);
    }

    @Override
    public UserWithRolesDTO updateUserStatus(Long userId, UpdateUserStatusRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy người dùng với ID: " + userId));

        user.setEnabled(request.getEnabled());
        userRepository.save(user);
        return userWithRolesMapper.toDTO(user);
    }

    @Override
    public UserWithRolesDTO updateUserRoles(Long userId, UpdateUserRolesRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy người dùng với ID: " + userId));

        Set<Role> newRoles = request.getRoleCodes().stream()
                .map(roleCode -> roleRepository.findByCode(roleCode))
                .filter(java.util.Objects::nonNull)
                .collect(Collectors.toSet());

        user.setRoles(newRoles);
        userRepository.save(user);
        return userWithRolesMapper.toDTO(user);
    }
}