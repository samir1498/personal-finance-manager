package com.samir.pfm;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

@TestConfiguration(proxyBeanMethods = false)
public class TestcontainersConfiguration {

    @Bean
    @ServiceConnection
    public PostgreSQLContainer<?> postgresContainer() {
        // Using locally available image to avoid network pulls
        var container = new PostgreSQLContainer<>(DockerImageName.parse("postgres:17"));
        // Ensure Flyway runs on this container
        container.withDatabaseName("pfm_db");
        container.withUsername("sa");
        container.withPassword("password");
        return container;
    }
}
