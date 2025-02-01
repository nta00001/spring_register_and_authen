package com.map_properties.spring_server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.map_properties.spring_server.entity.Role;
import com.map_properties.spring_server.enums.ERole;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByCode(ERole code);
}
