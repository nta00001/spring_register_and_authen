package com.map_properties.spring_server.service;

import com.map_properties.spring_server.dto.ListResponseDTO;
import com.map_properties.spring_server.dto.UpdateUserRolesRequest;
import com.map_properties.spring_server.dto.UpdateUserStatusRequest;
import com.map_properties.spring_server.dto.UserWithRolesDTO;
import com.map_properties.spring_server.request.FilterUserRequest;

public interface AdminUserService {
    ListResponseDTO<UserWithRolesDTO> getAllUsers(FilterUserRequest request);
    UserWithRolesDTO updateUserStatus(Long userId, UpdateUserStatusRequest request);
    UserWithRolesDTO updateUserRoles(Long userId, UpdateUserRolesRequest request);
}