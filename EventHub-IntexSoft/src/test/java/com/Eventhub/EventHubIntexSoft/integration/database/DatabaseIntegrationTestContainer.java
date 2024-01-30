package com.Eventhub.EventHubIntexSoft.integration.database;

import com.Eventhub.EventHubIntexSoft.EventHubIntexSoftApplication;
import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.configuration.Orthography;
import com.github.database.rider.junit5.api.DBRider;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@DBRider
@DBUnit(caseInsensitiveStrategy = Orthography.LOWERCASE)
@Testcontainers
@SpringBootTest(
    classes = EventHubIntexSoftApplication.class,
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DatabaseIntegrationTestContainer {
  @LocalServerPort protected Integer port;

  @Container @ServiceConnection
  private static final PostgreSQLContainer<?> postgres =
      new PostgreSQLContainer<>(DockerImageName.parse("postgres:16"));

  @BeforeEach
  void setUp() {
    RestAssured.baseURI = "http://localhost:" + port;
  }
}
