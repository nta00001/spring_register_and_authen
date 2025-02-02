package com.map_properties.spring_server.mapper;

import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.map_properties.spring_server.dto.RoleDTO;
import com.map_properties.spring_server.dto.UserWithRolesDTO;
import com.map_properties.spring_server.entity.User;

@Component
public class UserWithRolesMapperImpl implements UserWithRolesMapper {
    public UserWithRolesDTO toDTO(User user) {
        UserWithRolesDTO.UserWithRolesDTOBuilder userBuilder = UserWithRolesDTO.builder();
        userBuilder.uuid(user.getUuid().toString());
        userBuilder.email(user.getEmail());
        userBuilder.avatar_url(user.getAvatarUrl());
        userBuilder.roles(user.getRoles().stream().map(role -> new RoleDTO(role)).collect(Collectors.toSet()));
        return userBuilder.build();
    }
}
