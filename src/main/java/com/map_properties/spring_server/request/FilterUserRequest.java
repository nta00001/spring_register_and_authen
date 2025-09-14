package com.map_properties.spring_server.request;

import com.map_properties.spring_server.request.base.BaseListRequest;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Sort;

@Getter
@Setter
public class FilterUserRequest extends BaseListRequest {

    private String name;
    private String email;

    @Override
    protected Sort getDefaultSort() {
        return Sort.by(Sort.Direction.DESC, "createdAt");
    }
}