package com.map_properties.spring_server.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaginationDTO {
    private Integer count;
    private Boolean hasMoreItems;
    private Integer itemsPerPage;
    private Integer page;
    private Long total;
    private Integer totalPage;
}
