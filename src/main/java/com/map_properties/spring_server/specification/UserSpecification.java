package com.map_properties.spring_server.specification;

import com.map_properties.spring_server.entity.User;
import com.map_properties.spring_server.specification.base.BaseSpecification;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecification extends BaseSpecification {

    public static Specification<User> filterByName(String name) {
        return (root, query, cb) -> {
            if (name == null || name.trim().isEmpty()) {
                return cb.conjunction();
            }
            return cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");
        };
    }

    public static Specification<User> filterByEmail(String email) {
        return (root, query, cb) -> {
            if (email == null || email.trim().isEmpty()) {
                return cb.conjunction();
            }
            return cb.like(cb.lower(root.get("email")), "%" + email.toLowerCase() + "%");
        };
    }
}