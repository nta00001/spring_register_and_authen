package com.map_properties.spring_server.specification;

import org.springframework.data.jpa.domain.Specification;

import com.map_properties.spring_server.specification.base.BaseSpecification;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

public class RoleSpecification extends BaseSpecification {

    public static <T> Specification<T> filterName(String name) {
        return (Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            if (name == null || name.trim().isEmpty() || name.isBlank())
                return null;
            return cb.like(
                    cb.lower(root.get("name")),
                    "%" + name.toLowerCase() + "%");
        };
    }

    public static <T> Specification<T> filterCode(String code) {
        return (Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            if (code == null || code.trim().isEmpty() || code.isBlank())
                return null;
            return cb.like(
                    cb.lower(root.get("code")),
                    "%" + code.toLowerCase() + "%");
        };
    }

    public static <T> Specification<T> filterSortNumber(Integer sort) {
        return (Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            if (sort == null)
                return null;
            return cb.equal(root.get("sort"), sort);
        };
    }
}
