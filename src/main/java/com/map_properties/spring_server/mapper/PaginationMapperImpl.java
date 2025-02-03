package com.map_properties.spring_server.mapper;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.map_properties.spring_server.dto.PaginationDTO;

@Component
public class PaginationMapperImpl<T> implements PaginationMapper<T> {
    public PaginationDTO toDTO(Page<T> entity) {
        PaginationDTO.PaginationDTOBuilder builder = PaginationDTO.builder();
        builder.count(entity.getNumberOfElements());
        builder.hasMoreItems(entity.hasNext());
        builder.itemsPerPage(entity.getSize());
        builder.page(entity.getNumber() + 1);
        builder.total(entity.getTotalElements());
        builder.totalPage(entity.getTotalPages());
        return builder.build();
    }
}
