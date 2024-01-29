package com.Eventhub.EventHubIntexSoft.controller;

import com.Eventhub.EventHubIntexSoft.EventHubIntexSoftApplication;
import com.Eventhub.EventHubIntexSoft.dto.UserDto;
import com.Eventhub.EventHubIntexSoft.entity.User;
import com.Eventhub.EventHubIntexSoft.repository.UserRepository;
import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.configuration.Orthography;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.junit5.api.DBRider;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.util.ArrayList;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.HttpStatus;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@Testcontainers
@DBRider
@DBUnit(caseInsensitiveStrategy = Orthography.LOWERCASE)
@SpringBootTest(
    classes = EventHubIntexSoftApplication.class,
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerIntegrationTest {
  @LocalServerPort private Integer port;

  @Autowired UserRepository userRepository;

  @Container @ServiceConnection
  private static final PostgreSQLContainer<?> postgres =
      new PostgreSQLContainer<>(DockerImageName.parse("postgres:16"));

  @BeforeEach
  void setUp() {
    RestAssured.baseURI = "http://localhost:" + port;
  }

  @Test
  @DataSet(cleanBefore = true)
  void testEmptyGetAllUsers() throws Exception {
    RestAssured.given()
        .contentType(ContentType.JSON)
        .when()
        .get("/user/all")
        .then()
        .statusCode(HttpStatus.OK.value())
        .body(Matchers.matchesPattern("\\[\\]"));
  }

  @Test
  @DataSet(value = "datasets/yml/users.yml", cleanBefore = true, cleanAfter = true)
  void testGetAll() throws Exception {
    RestAssured.given()
        .contentType(ContentType.JSON)
        .when()
        .get("/user/all")
        .then()
        .statusCode(HttpStatus.OK.value())
        .body("userName", Matchers.hasItems("Pablo", "Entony"));
  }

  @Test
  @DataSet(value = "datasets/yml/users.yml", cleanBefore = true, cleanAfter = true)
  void testCreateNotExistUser() {
    RestAssured.given()
        .contentType(ContentType.JSON)
        .given()
        .body(
            new User(
                14L,
                "Benedicto",
                "example123@gmail.com",
                "QWERTYqwerty",
                new ArrayList<>(),
                new ArrayList<>()))
        .when()
        .post("/user")
        .then()
        .statusCode(HttpStatus.OK.value())
        .body("email", Matchers.equalTo("example123@gmail.com"));
  }

  @Test
  @DataSet(value = "datasets/yml/users.yml", cleanBefore = true, cleanAfter = true)
  void testCreateExistUser() {
    RestAssured.given()
        .contentType(ContentType.JSON)
        .body(
            new User(
                321L,
                "Pablo",
                "pablo123@mail.de",
                "qwerty1234",
                new ArrayList<>(),
                new ArrayList<>()))
        .when()
        .post("/user")
        .then()
        .statusCode(HttpStatus.CONFLICT.value());
  }

  @Test
  @DataSet(value = "datasets/yml/users.yml", cleanBefore = true, cleanAfter = true)
  void getExistUserById() {
    RestAssured.given()
        .contentType(ContentType.JSON)
        .when()
        .get("/user/321")
        .then()
        .statusCode(HttpStatus.OK.value())
        .body("userName", Matchers.equalTo("Pablo"))
        .body("email", Matchers.equalTo("pablo123@mail.de"));
  }

  @Test
  @DataSet(value = "datasets/yml/users.yml", cleanBefore = true, cleanAfter = true)
  void getNotExistUserById() {
    RestAssured.given()
        .contentType(ContentType.JSON)
        .when()
        .get("/user/320")
        .then()
        .statusCode(HttpStatus.NOT_FOUND.value());
  }

  @Test
  @DataSet(value = "datasets/yml/users.yml", cleanBefore = true, cleanAfter = true)
  void updateExistUserWithUniqData() {
    RestAssured.given()
        .contentType(ContentType.JSON)
        .body(new UserDto(321L, "Petro", null, "12345", new ArrayList<>(), new ArrayList<>()))
        .when()
        .put("/user")
        .then()
        .statusCode(HttpStatus.OK.value())
        .body("userId", Matchers.equalTo(321))
        .body("userName", Matchers.equalTo("Petro"))
        .body("password", Matchers.equalTo("12345"));
  }

  @Test
  @DataSet(value = "datasets/yml/users.yml", cleanBefore = true, cleanAfter = true)
  void updateExistUserWithNonUniqData() {
    RestAssured.given()
        .contentType(ContentType.JSON)
        .body(
            new UserDto(
                321L, null, "ento912@mail.fe", "12345", new ArrayList<>(), new ArrayList<>()))
        .when()
        .put("/user")
        .then()
        .statusCode(HttpStatus.NOT_FOUND.value());
  }

  @Test
  @DataSet(value = "datasets/yml/users.yml", cleanBefore = true, cleanAfter = true)
  void updateNonExistUser() {
    RestAssured.given()
        .contentType(ContentType.JSON)
        .body(
            new UserDto(
                320L, null, "unique@mail.de", "12345", new ArrayList<>(), new ArrayList<>()))
        .when()
        .put("/user")
        .then()
        .statusCode(HttpStatus.NOT_FOUND.value());
  }

  @Test
  @DataSet(value = "datasets/yml/users.yml", cleanBefore = true, cleanAfter = true)
  void deleteExistUser() {
    RestAssured.given()
        .contentType(ContentType.JSON)
        .when()
        .delete("/user/321")
        .then()
        .statusCode(HttpStatus.OK.value());
  }

  @Test
  @DataSet(value = "datasets/yml/users.yml", cleanBefore = true, cleanAfter = true)
  void deleteNonExistUser() {
    RestAssured.given()
        .contentType(ContentType.JSON)
        .when()
        .delete("/user/320")
        .then()
        .statusCode(HttpStatus.NOT_FOUND.value());
  }
}
