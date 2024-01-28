package com.Eventhub.EventHubIntexSoft.controller;

import com.Eventhub.EventHubIntexSoft.EventHubIntexSoftApplication;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@Testcontainers
@SpringBootTest(classes = EventHubIntexSoftApplication.class)
@AutoConfigureMockMvc
public class EventControllerIntegrationTest {
  @LocalServerPort
  private Integer port;

  private MockMvc mockMvc;
  @Container
  @ServiceConnection
  private static PostgreSQLContainer<?> postgres =
          new PostgreSQLContainer<>(DockerImageName.parse("postgres:16"));

//  @BeforeEach
//  void setUp() {
//    RestAssured.baseURI = "http://localhost:" + port;
////    userRepository.deleteAll();
//  }
}
