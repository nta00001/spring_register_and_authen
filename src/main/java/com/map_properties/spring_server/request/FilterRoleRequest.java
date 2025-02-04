package com.map_properties.spring_server.request;

import java.util.Date;

import org.springframework.data.domain.Sort;

import com.map_properties.spring_server.request.base.BaseListRequest;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FilterRoleRequest extends BaseListRequest {
    private Long id;
    private String name;
    private String code;
    private Integer sort;

    @Override
    public Sort getDefaultSort() {
        Sort currentSort = Sort.by("id");
        if (this.isDesc) {
            currentSort.descending();
        } else {
            currentSort.ascending();
        }
        return currentSort;
    }
}
