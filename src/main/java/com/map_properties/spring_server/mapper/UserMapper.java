package com.map_properties.spring_server.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.map_properties.spring_server.dto.UserDTO;
import com.map_properties.spring_server.entity.User;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface UserMapper {
    UserDTO toDTO(User user);
}
