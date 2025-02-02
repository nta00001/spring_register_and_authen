package com.map_properties.spring_server.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.map_properties.spring_server.dto.UserWithRolesDTO;
import com.map_properties.spring_server.entity.User;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface UserWithRolesMapper {
    UserWithRolesDTO toDTO(User user);
}
