package com.map_properties.spring_server.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.map_properties.spring_server.entity.Role;
import com.map_properties.spring_server.mapper.ListResponseMapper;
import com.map_properties.spring_server.mapper.RoleMapper;
import com.map_properties.spring_server.repository.RoleRepository;
import com.map_properties.spring_server.service.RoleService;
import com.map_properties.spring_server.specification.RoleSpecification;
import com.map_properties.spring_server.request.FilterRoleRequest;
import com.map_properties.spring_server.dto.ListResponseDTO;
import com.map_properties.spring_server.dto.RoleDTO;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    RoleMapper roleMapper;

    @Autowired
    ListResponseMapper<RoleDTO> listResponseMapper;

    public ListResponseDTO<RoleDTO> getRoles(FilterRoleRequest request) {
        Specification<Role> spec = RoleSpecification.build();
        if (request == null) {
            Page<RoleDTO> pageRoles = roleRepository.findAll(spec, new FilterRoleRequest().toPageable())
                    .map(roleMapper::toDTO);
            ListResponseDTO<RoleDTO> listResponse = listResponseMapper.toDTO(pageRoles);
            return listResponse;
        }
        if (request.getStartCreatedAtDate() != null || request.getEndCreatedAtDate() != null) {
            spec = spec.and(
                    RoleSpecification.filterCreatedAt(request.getStartCreatedAtDate(), request.getEndCreatedAtDate()));
        }
        if (request.getStartUpdatedAtDate() != null || request.getEndUpdatedAtDate() != null) {
            spec = spec.and(
                    RoleSpecification.filterUpdatedAt(request.getStartUpdatedAtDate(), request.getEndUpdatedAtDate()));
        }
        if (request.getCode() != null) {
            spec = spec.and(
                    RoleSpecification.filterCode(request.getCode()));
        }
        if (request.getName() != null) {
            spec = spec.and(
                    RoleSpecification.filterName(request.getName()));
        }
        if (request.getSort() != null) {
            spec = spec.and(
                    RoleSpecification.filterSortNumber(request.getSort()));
        }

        Page<RoleDTO> pageRoles = roleRepository.findAll(spec, request.toPageable()).map(roleMapper::toDTO);
        ListResponseDTO<RoleDTO> listResponse = listResponseMapper.toDTO(pageRoles);
        return listResponse;
    }
}
