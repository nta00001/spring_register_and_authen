package com.map_properties.spring_server.service;

import com.map_properties.spring_server.dto.ListResponseDTO;
import com.map_properties.spring_server.dto.RoleDTO;
import com.map_properties.spring_server.request.FilterRoleRequest;

public interface RoleService {
    ListResponseDTO<RoleDTO> getRoles(FilterRoleRequest request);
}
