package com.map_properties.spring_server.mapper;

import org.springframework.stereotype.Component;

import com.map_properties.spring_server.dto.UserDTO;
import com.map_properties.spring_server.entity.User;

@Component
public class UserMapperImpl implements UserMapper {
    public UserDTO toDTO(User user) {
        UserDTO.UserDTOBuilder userBuilder = UserDTO.builder();
        userBuilder.uuid(user.getUuid().toString());
        userBuilder.email(user.getEmail());
        userBuilder.avatar_url(user.getAvatarUrl());
        return userBuilder.build();
    }
}
