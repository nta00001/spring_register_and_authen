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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import com.map_properties.spring_server.entity.Role;
import com.map_properties.spring_server.entity.User;
import com.map_properties.spring_server.enums.ERole;
import com.map_properties.spring_server.repository.RoleRepository;
import com.map_properties.spring_server.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class UserSeeder implements ApplicationRunner {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder encoder;

    @SuppressWarnings("unchecked")
    Map<String, Object>[] _users = new HashMap[] {
            new HashMap<String, Object>() {
                {
                    put("name", "administrator");
                    put("email", "sysadmin@hp.com");
                    put("password", "12345678");
                    put("roles", new ERole[] { ERole.admin });
                }
            },
            new HashMap<String, Object>() {
                {
                    put("name", "ChiCong");
                    put("email", "chicongpham235@gmail.com");
                    put("password", "12345678");
                    put("roles", new ERole[] { ERole.admin, ERole.user });
                }
            }
    };

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (args.getOptionValues("seeder") == null)
            return;
        List<String> seeder = Arrays.asList(args.getOptionValues("seeder").get(0).split(","));
        if (seeder.contains("user")) {
            seed();
            logger.info("Success run user seeder");
        }
    }

    @Transactional
    public void seed() {
        for (Map<String, Object> _user : _users) {
            User user = userRepository.findByEmail((String) _user.get("email"));
            if (user != null) {
                continue;
            }
            final User finalUser = new User();
            finalUser.setName((String) _user.get("name"));
            finalUser.setEmail((String) _user.get("email"));
            finalUser.setPassword(encoder.encode((String) _user.get("password")));
            Arrays.asList((ERole[]) _user.get("roles")).forEach(_role -> {
                Role role = roleRepository.findByCode(_role);
                if (role != null) {
                    finalUser.addRole(role);
                }
            });
            userRepository.save(finalUser);
        }
    }
}
