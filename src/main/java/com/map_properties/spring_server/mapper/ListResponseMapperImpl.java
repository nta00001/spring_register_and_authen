package com.map_properties.spring_server.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.map_properties.spring_server.dto.ListResponseDTO;
import com.map_properties.spring_server.dto.PaginationDTO;

@Component
public class ListResponseMapperImpl<T> implements ListResponseMapper<T> {

    @Autowired
    PaginationMapper<T> paginationMapper;

    @Override
    public ListResponseDTO<T> toDTO(Page<T> entity) {
        ListResponseDTO.ListResponseDTOBuilder<T> builder = ListResponseDTO.builder();
        builder.list(entity.getContent());
        PaginationDTO paginationDTO = paginationMapper.toDTO(entity);
        builder.pagination(paginationDTO);
        return builder.build();
    }
}
