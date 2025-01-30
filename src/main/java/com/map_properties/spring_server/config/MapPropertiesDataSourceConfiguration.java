package com.map_properties.spring_server.config;

import java.util.Objects;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.orm.jpa.JpaTransactionManager;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = { "com.map_properties.spring_server.repository",
        "com.map_properties.spring_server.repository.impl" }, entityManagerFactoryRef = "mapPropertiesEntityManagerFactory", transactionManagerRef = "mapPropertiesTransactionManager")
public class MapPropertiesDataSourceConfiguration {
    @Bean(name = "mapPropertiesDataSourceProperties")
    @ConfigurationProperties("spring.datasource")
    DataSourceProperties mapPropertiesDataSourceProperties() {
        DataSourceProperties properties = new DataSourceProperties();
        return properties;
    }

    @Bean(name = "mapPropertiesDataSource")
    @ConfigurationProperties("spring.datasource.configuration")
    @Primary
    DataSource mapPropertiesDataSource() {
        DataSourceProperties properties = mapPropertiesDataSourceProperties();
        return properties.initializeDataSourceBuilder().build();
    }

    @Primary
    @Bean(name = "mapPropertiesEntityManagerFactory")
    LocalContainerEntityManagerFactoryBean mapPropertiesEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("mapPropertiesDataSource") DataSource dataSource) {
        return builder
                .dataSource(dataSource)
                .packages("com.map_properties.spring_server.entity")
                .persistenceUnit("mapProperties")
                .build();
    }

    @Primary
    @Bean(name = "mapPropertiesTransactionManager")
    PlatformTransactionManager mapPropertiesTransactionManager(
            @Qualifier("mapPropertiesEntityManagerFactory") LocalContainerEntityManagerFactoryBean mapPropertiesEntityManagerFactory) {
        return new JpaTransactionManager(Objects.requireNonNull(mapPropertiesEntityManagerFactory.getObject()));
    }
}
