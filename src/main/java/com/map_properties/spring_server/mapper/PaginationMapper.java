package com.map_properties.spring_server.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.domain.Page;

import com.map_properties.spring_server.dto.PaginationDTO;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface PaginationMapper<T> {
    PaginationDTO toDTO(Page<T> entity);
}
