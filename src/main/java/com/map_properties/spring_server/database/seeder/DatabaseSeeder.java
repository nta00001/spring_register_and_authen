package com.map_properties.spring_server.database.seeder;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;

import com.map_properties.spring_server.database.seeder.Access.RoleSeeder;
import com.map_properties.spring_server.database.seeder.Access.UserSeeder;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class DatabaseSeeder implements ApplicationRunner {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    UserSeeder userSeeder;

    @Autowired
    RoleSeeder roleSeeder;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (args.getOptionValues("seeder") == null)
            return;
        List<String> seeder = Arrays.asList(args.getOptionValues("seeder").get(0).split(","));
        if (seeder.contains("all")) {
            seed();
            logger.info("Success run all seeder");
        }
    }

    public void seed() {
        roleSeeder.seed();
        userSeeder.seed();
    }
}
