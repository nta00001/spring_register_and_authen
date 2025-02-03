package com.map_properties.spring_server.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.map_properties.spring_server.dto.RoleDTO;
import com.map_properties.spring_server.entity.Role;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface RoleMapper {
    RoleDTO toDTO(Role role);

    Role toEntity(RoleDTO roleDTO);
}
