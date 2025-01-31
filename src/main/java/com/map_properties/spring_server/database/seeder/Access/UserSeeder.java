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

import com.map_properties.spring_server.entity.User;
import com.map_properties.spring_server.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class UserSeeder implements ApplicationRunner {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    UserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;

    @SuppressWarnings("unchecked")
    Map<String, Object>[] _users = new HashMap[] {
            new HashMap<String, Object>() {
                {
                    put("name", "administrator");
                    put("email", "sysadmin@hp.com");
                    put("password", "12345678");
                    put("is_admin", true);
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

    public void seed() {
        for (Map<String, Object> _user : _users) {
            User user = userRepository.findByEmail((String) _user.get("email"));
            if (user == null) {
                user = new User();
                user.setName((String) _user.get("name"));
                user.setEmail((String) _user.get("email"));
                user.setPassword(encoder.encode((String) _user.get("password")));
                user.setIsAdmin((Boolean) _user.get("is_admin"));
                userRepository.save(user);
            }
        }
    }
}
