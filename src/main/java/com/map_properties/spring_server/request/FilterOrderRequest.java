package com.map_properties.spring_server.request;

import com.map_properties.spring_server.enums.EOrderStatus;
import com.map_properties.spring_server.request.base.BaseListRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Sort;

@Getter
@Setter
@NoArgsConstructor
public class FilterOrderRequest extends BaseListRequest {

    private Long userId;
    private EOrderStatus status;

    @Override
    public Sort getDefaultSort() {
        // Mặc định sắp xếp theo ngày tạo mới nhất
        return Sort.by(Sort.Direction.DESC, "createdAt");
    }
}