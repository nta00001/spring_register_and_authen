package com.map_properties.spring_server.mapper;

import org.springframework.stereotype.Component;

import com.map_properties.spring_server.dto.RoleDTO;
import com.map_properties.spring_server.entity.Role;

@Component
public class RoleMapperImpl implements RoleMapper {
    public RoleDTO toDTO(Role role) {
        if (role == null)
            return null;
        RoleDTO.RoleDTOBuilder builder = RoleDTO.builder();
        builder.id(role.getId());
        builder.name(role.getName());
        builder.code(role.getCode());
        builder.sort(role.getSort());
        builder.createdAt(role.getCreatedAt());
        builder.updatedAt(role.getUpdatedAt());
        return builder.build();
    }

    public Role toEntity(RoleDTO dto) {
        if (dto == null)
            return null;
        Role.RoleBuilder builder = Role.builder();
        builder.id(dto.getId());
        builder.name(dto.getName());
        builder.code(dto.getCode());
        builder.sort(dto.getSort());
        builder.createdAt(dto.getCreatedAt());
        builder.updatedAt(dto.getUpdatedAt());
        return builder.build();
    }
}
