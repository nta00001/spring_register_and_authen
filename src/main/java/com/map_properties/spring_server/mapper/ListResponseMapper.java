package com.map_properties.spring_server.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.domain.Page;

import com.map_properties.spring_server.dto.ListResponseDTO;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface ListResponseMapper<T> {
    ListResponseDTO<T> toDTO(Page<T> entity);
}
