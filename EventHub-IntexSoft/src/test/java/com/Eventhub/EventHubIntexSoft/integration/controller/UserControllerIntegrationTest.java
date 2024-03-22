package com.Eventhub.EventHubIntexSoft.integration.controller;

import com.Eventhub.EventHubIntexSoft.EventHubIntexSoftApplication;
import com.Eventhub.EventHubIntexSoft.dto.UserDto;
import com.Eventhub.EventHubIntexSoft.integration.config.TestDatabaseConfiguration;
import com.Eventhub.EventHubIntexSoft.repository.UserRepository;
import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.configuration.Orthography;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.junit5.api.DBRider;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;

@DBRider
@DBUnit(caseInsensitiveStrategy = Orthography.LOWERCASE)
@SpringBootTest(
    classes = EventHubIntexSoftApplication.class,
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(TestDatabaseConfiguration.class)
public class UserControllerIntegrationTest {

  @LocalServerPort protected Integer port;
  //todo обработать всё что связано с ролями
  @BeforeEach
  void setUp() {
    RestAssured.baseURI = "http://localhost:" + port;
  }

  @Autowired UserRepository userRepository;

  @Test
  @DataSet(executeScriptsBefore = "scripts/truncateUsers.sql")
  void testEmptyGetAllUsers() {
    RestAssured.given()
        .contentType(ContentType.JSON)
        .when()
        .get("/user/all")
        .then()
        .statusCode(HttpStatus.OK.value())
        .body(Matchers.matchesPattern("\\[\\]"));
  }

  @Test
  @DataSet(
      value = "datasets/yml/users.yml",
      executeScriptsBefore = "scripts/truncateUsers.sql",
      executeScriptsAfter = "scripts/truncateUsers.sql")
  void testGetAllUsers() {
    RestAssured.given()
        .contentType(ContentType.JSON)
        .when()
        .get("/user/all")
        .then()
        .statusCode(HttpStatus.OK.value())
        .body("userName", Matchers.hasItems("Pablo", "Entony"));
  }

  @Test
  @DataSet(
      value = "datasets/yml/users.yml",
      executeScriptsBefore = "scripts/truncateUsers.sql",
      executeScriptsAfter = "scripts/truncateUsers.sql")
  void testCreateNotExistUser() {
    UserDto userDto = new UserDto();
    userDto.setUserId(14L);
    userDto.setUsername("Benedicto");
    userDto.setEmail("example123@gmail.com");
    userDto.setPassword("QWERTY%qwerty12");
    RestAssured.given()
        .contentType(ContentType.JSON)
        .given()
        .body(userDto)
        .when()
        .post("/user")
        .then()
        .statusCode(HttpStatus.OK.value())
        .body("userId", Matchers.anything())
        .body("userName", Matchers.equalTo("Benedicto"))
        .body("password", Matchers.equalTo("QWERTY%qwerty12"))
        .body("email", Matchers.equalTo("example123@gmail.com"));
  }

  @Test
  @DataSet(
      value = "datasets/yml/users.yml",
      executeScriptsBefore = "scripts/truncateUsers.sql",
      executeScriptsAfter = "scripts/truncateUsers.sql")
  void testCreateExistUser() {
    UserDto userDto = new UserDto();
    userDto.setUserId(null);
    userDto.setUsername("Pablo");
    userDto.setEmail("pablo123@mail.de");
    userDto.setPassword("qWErty%1234");
    RestAssured.given()
        .contentType(ContentType.JSON)
        .body(userDto)
        .when()
        .post("/user")
        .then()
        .statusCode(HttpStatus.NOT_ACCEPTABLE.value());
  }

  @Test
  @DataSet(
      value = "datasets/yml/users.yml",
      executeScriptsBefore = "scripts/truncateUsers.sql",
      executeScriptsAfter = "scripts/truncateUsers.sql")
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
  @DataSet(
      value = "datasets/yml/users.yml",
      executeScriptsBefore = "scripts/truncateUsers.sql",
      executeScriptsAfter = "scripts/truncateUsers.sql")
  void getNotExistUserById() {
    RestAssured.given()
        .contentType(ContentType.JSON)
        .when()
        .get("/user/320")
        .then()
        .statusCode(HttpStatus.NOT_FOUND.value());
  }

  @Test
  @DataSet(
      value = "datasets/yml/users.yml",
      executeScriptsBefore = "scripts/truncateUsers.sql",
      executeScriptsAfter = "scripts/truncateUsers.sql")
  void updateExistUserWithUniqData() {
    UserDto userDto = new UserDto();
    userDto.setUserId(321L);
    userDto.setUsername("Petro");
    userDto.setEmail("petro1989@mail.de");
    userDto.setPassword("qWEloy%8235");
    RestAssured.given()
        .contentType(ContentType.JSON)
        .body(userDto)
        .when()
        .put("/user")
        .then()
        .statusCode(HttpStatus.OK.value())
        .body("userId", Matchers.equalTo(321))
        .body("userName", Matchers.equalTo("Petro"))
        .body("email", Matchers.equalTo("petro1989@mail.de"))
        .body("password", Matchers.equalTo("qWEloy%8235"));
  }

  @Test
  @DataSet(
      value = "datasets/yml/users.yml",
      executeScriptsBefore = "scripts/truncateUsers.sql",
      executeScriptsAfter = "scripts/truncateUsers.sql")
  void updateExistUserWithNonUniqData() {
    UserDto userDto = new UserDto();
    userDto.setUserId(321L);
    userDto.setUsername("Pablo");
    userDto.setEmail("ento912@mail.fe");
    userDto.setPassword("qWEloy%8235");
    RestAssured.given()
        .contentType(ContentType.JSON)
        .body(userDto)
        .when()
        .put("/user")
        .then()
        .statusCode(HttpStatus.NOT_ACCEPTABLE.value());
  }

  @Test
  @DataSet(
      value = "datasets/yml/users.yml",
      executeScriptsBefore = "scripts/truncateUsers.sql",
      executeScriptsAfter = "scripts/truncateUsers.sql")
  void updateNonExistUser() {
    UserDto userDto = new UserDto();
    userDto.setUserId(320L);
    userDto.setUsername("Mazeratti");
    userDto.setEmail("petro1989@mail.de");
    userDto.setPassword("qWEloy%8235");
    RestAssured.given()
        .contentType(ContentType.JSON)
        .body(userDto)
        .when()
        .put("/user")
        .then()
        .statusCode(HttpStatus.NOT_FOUND.value());
  }

  void patchExistUserWithUniqData() {
    UserDto userDto = new UserDto();
    userDto.setUserId(321L);
    userDto.setUsername("Petro");
    userDto.setEmail(null);
    userDto.setPassword("qWEloy%8235");
    RestAssured.given()
        .contentType(ContentType.JSON)
        .body(userDto)
        .when()
        .patch("/user")
        .then()
        .statusCode(HttpStatus.OK.value())
        .body("userId", Matchers.equalTo(321))
        .body("userName", Matchers.equalTo("Petro"))
        .body("email", Matchers.equalTo("pablo123@mail.de"))
        .body("password", Matchers.equalTo("qWEloy%8235"));
  }

  @Test
  @DataSet(
      value = "datasets/yml/users.yml",
      executeScriptsBefore = "scripts/truncateUsers.sql",
      executeScriptsAfter = "scripts/truncateUsers.sql")
  void patchExistUserWithNonUniqData() {
    UserDto userDto = new UserDto();
    userDto.setUserId(321L);
    userDto.setUsername("Pablo");
    userDto.setEmail("ento912@mail.fe");
    userDto.setPassword(null);
    RestAssured.given()
        .contentType(ContentType.JSON)
        .body(userDto)
        .when()
        .patch("/user")
        .then()
        .statusCode(HttpStatus.NOT_ACCEPTABLE.value());
  }

  @Test
  @DataSet(
      value = "datasets/yml/users.yml",
      executeScriptsBefore = "scripts/truncateUsers.sql",
      executeScriptsAfter = "scripts/truncateUsers.sql")
  void patchNonExistUser() {
    UserDto userDto = new UserDto();
    userDto.setUserId(320L);
    userDto.setUsername("Mazeratti");
    userDto.setEmail(null);
    userDto.setPassword(null);
    RestAssured.given()
        .contentType(ContentType.JSON)
        .body(userDto)
        .when()
        .patch("/user")
        .then()
        .statusCode(HttpStatus.NOT_FOUND.value());
  }

  @Test
  @DataSet(
      value = "datasets/yml/users.yml",
      executeScriptsBefore = "scripts/truncateUsers.sql",
      executeScriptsAfter = "scripts/truncateUsers.sql")
  void deleteExistUser() {
    RestAssured.given()
        .contentType(ContentType.JSON)
        .when()
        .delete("/user/321")
        .then()
        .statusCode(HttpStatus.OK.value());
  }

  @Test
  @DataSet(
      value = "datasets/yml/users.yml",
      executeScriptsBefore = "scripts/truncateUsers.sql",
      executeScriptsAfter = "scripts/truncateUsers.sql")
  void deleteNonExistUser() {
    RestAssured.given()
        .contentType(ContentType.JSON)
        .when()
        .delete("/user/320")
        .then()
        .statusCode(HttpStatus.NOT_FOUND.value());
  }
}
