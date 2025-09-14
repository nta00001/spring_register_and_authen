package com.map_properties.spring_server.specification;

import com.map_properties.spring_server.entity.Order;
import com.map_properties.spring_server.enums.EOrderStatus;
import com.map_properties.spring_server.specification.base.BaseSpecification;
import org.springframework.data.jpa.domain.Specification;

public class OrderSpecification extends BaseSpecification {

    public static Specification<Order> hasUserId(Long userId) {
        return (root, query, cb) -> {
            if (userId == null) {
                return cb.conjunction();
            }
            return cb.equal(root.get("user").get("id"), userId);
        };
    }

    public static Specification<Order> hasStatus(EOrderStatus status) {
        return (root, query, cb) -> {
            if (status == null) {
                return cb.conjunction();
            }
            return cb.equal(root.get("status"), status);
        };
    }
}