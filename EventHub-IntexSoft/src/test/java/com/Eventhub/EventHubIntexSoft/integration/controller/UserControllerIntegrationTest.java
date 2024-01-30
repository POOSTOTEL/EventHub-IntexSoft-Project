package com.Eventhub.EventHubIntexSoft.integration.controller;

import com.Eventhub.EventHubIntexSoft.dto.UserDto;
import com.Eventhub.EventHubIntexSoft.integration.database.DatabaseIntegrationTestContainer;
import com.Eventhub.EventHubIntexSoft.repository.UserRepository;
import com.github.database.rider.core.api.dataset.DataSet;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

public class UserControllerIntegrationTest extends DatabaseIntegrationTestContainer {

  @Autowired UserRepository userRepository;

  @Test
  @DataSet(cleanBefore = true)
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
  @DataSet(value = "datasets/yml/users.yml", cleanBefore = true, cleanAfter = true)
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
  @DataSet(value = "datasets/yml/users.yml", cleanBefore = true, cleanAfter = true)
  void testCreateNotExistUser() {
    UserDto userDto = new UserDto();
    userDto.setUserId(14L);
    userDto.setUserName("Benedicto");
    userDto.setEmail("example123@gmail.com");
    userDto.setPassword("QWERTYqwerty");
    RestAssured.given()
        .contentType(ContentType.JSON)
        .given()
        .body(userDto)
        .when()
        .post("/user")
        .then()
        .statusCode(HttpStatus.OK.value())
        .body("email", Matchers.equalTo("example123@gmail.com"));
  }

  @Test
  @DataSet(value = "datasets/yml/users.yml", cleanBefore = true, cleanAfter = true)
  void testCreateExistUser() {
    UserDto userDto = new UserDto();
    userDto.setUserId(321L);
    userDto.setUserName("Pablo");
    userDto.setEmail("pablo123@mail.de");
    userDto.setPassword("qwerty1234");
    RestAssured.given()
        .contentType(ContentType.JSON)
        .body(userDto)
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
    UserDto userDto = new UserDto();
    userDto.setUserId(321L);
    userDto.setUserName("Petro");
    userDto.setEmail(null);
    userDto.setPassword("12345");
    RestAssured.given()
        .contentType(ContentType.JSON)
        .body(userDto)
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
    UserDto userDto = new UserDto();
    userDto.setUserId(321L);
    userDto.setUserName(null);
    userDto.setEmail("ento912@mail.fe");
    userDto.setPassword("12345");
    RestAssured.given()
        .contentType(ContentType.JSON)
        .body(userDto)
        .when()
        .put("/user")
        .then()
        .statusCode(HttpStatus.NOT_FOUND.value());
  }

  @Test
  @DataSet(value = "datasets/yml/users.yml", cleanBefore = true, cleanAfter = true)
  void updateNonExistUser() {
    UserDto userDto = new UserDto();
    userDto.setUserId(320L);
    userDto.setUserName(null);
    userDto.setEmail("unique@mail.de");
    userDto.setPassword("12345");
    RestAssured.given()
        .contentType(ContentType.JSON)
        .body(userDto)
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
