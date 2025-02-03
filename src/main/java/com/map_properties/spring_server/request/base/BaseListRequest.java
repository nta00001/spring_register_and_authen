package com.map_properties.spring_server.request.base;

import java.util.Date;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public abstract class BaseListRequest {
    protected Integer page = 0;
    protected Integer size = 10;
    protected String sortBy = "";
    protected Boolean isDesc = false;

    protected Date startCreatedAtDate;
    protected Date endCreatedAtDate;

    protected Date startUpdatedAtDate;
    protected Date endUpdatedAtDate;

    public Pageable toPageable() {
        Integer page = (this.page != null & this.page >= 0) ? this.page : 0;
        Integer size = (this.size != null & this.size > 0) ? this.size : 10;
        Sort currentSortBy = this.getDefaultSort();
        if (sortBy != null && !sortBy.trim().isEmpty() && !sortBy.isBlank()) {
            currentSortBy = Sort.by(sortBy);
            if (isDesc) {
                currentSortBy.descending();
            } else {
                currentSortBy.ascending();
            }
        }
        return PageRequest.of(page, size, currentSortBy);
    }

    protected Sort getDefaultSort() {
        return Sort.unsorted();
    }
}
