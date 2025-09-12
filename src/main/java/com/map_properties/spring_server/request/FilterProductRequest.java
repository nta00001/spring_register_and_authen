package com.map_properties.spring_server.request;

import java.math.BigDecimal;

import org.springframework.data.domain.Sort;

import com.map_properties.spring_server.request.base.BaseListRequest;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FilterProductRequest extends BaseListRequest {

    private String name;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;

    @Override
    public Sort getDefaultSort() {
        // Mặc định sắp xếp theo ID, bạn có thể thay đổi thành 'name', 'price', v.v.
        Sort.Direction direction = this.isDesc ? Sort.Direction.DESC : Sort.Direction.ASC;
        return Sort.by(direction, "id");
    }
}