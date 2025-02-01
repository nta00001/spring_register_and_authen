package com.map_properties.spring_server.database.seeder.Access;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

import com.map_properties.spring_server.entity.Role;
import com.map_properties.spring_server.enums.ERole;
import com.map_properties.spring_server.repository.RoleRepository;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class RoleSeeder implements ApplicationRunner {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    RoleRepository roleRepository;

    @SuppressWarnings("unchecked")
    Map<String, Object>[] _roles = new HashMap[] {
            new HashMap<String, Object>() {
                {
                    put("code", ERole.ROLE_ADMIN.getCode());
                    put("name", ERole.ROLE_ADMIN.getName());
                    put("sort", 1);
                }
            },
            new HashMap<String, Object>() {
                {
                    put("code", ERole.ROLE_USER.getCode());
                    put("name", ERole.ROLE_USER.getName());
                    put("sort", 2);
                }
            }
    };

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (args.getOptionValues("seeder") == null)
            return;
        List<String> seeder = Arrays.asList(args.getOptionValues("seeder").get(0).split(","));
        if (seeder.contains("role")) {
            seed();
            logger.info("Success run role seeder");
        }
    }

    @Transactional
    public void seed() {
        for (Map<String, Object> _role : _roles) {
            Role role = roleRepository.findByCode((String) _role.get("code"));
            if (role == null) {
                role = new Role();
                role.setCode((String) _role.get("code"));
                role.setName((String) _role.get("name"));
                role.setSort((Integer) _role.get("sort"));
                roleRepository.save(role);
            }
        }
    }
}
