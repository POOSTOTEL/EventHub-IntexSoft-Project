package com.Eventhub.EventHubIntexSoft.integration.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.testcontainers.containers.PostgreSQLContainer;

@TestConfiguration
public class TestDatabaseConfiguration {

  private static final PostgreSQLContainer postgreSQLContainer =
      new PostgreSQLContainer<>("postgres:16")
          .withDatabaseName("eventhub")
          .withUsername("postgres")
          .withPassword("root");

  static {
    postgreSQLContainer.start();
    System.setProperty("spring.datasource.url", postgreSQLContainer.getJdbcUrl());
    System.setProperty("spring.datasource.password", postgreSQLContainer.getPassword());
    System.setProperty("spring.datasource.username", postgreSQLContainer.getUsername());
  }
}
