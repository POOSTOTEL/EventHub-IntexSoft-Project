package com.Eventhub.EventHubIntexSoft.integration.controller;

import com.Eventhub.EventHubIntexSoft.dto.EventDto;
import com.Eventhub.EventHubIntexSoft.integration.database.DatabaseIntegrationTestContainer;
import com.Eventhub.EventHubIntexSoft.repository.EventRepository;
import com.github.database.rider.core.api.dataset.DataSet;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.time.LocalDateTime;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

public class EventControllerIntegrationTest extends DatabaseIntegrationTestContainer {
  @Autowired EventRepository eventRepository;

  @Test
  @DataSet(cleanBefore = true)
  void testEmptyGetAllUsers() {
    RestAssured.given()
        .contentType(ContentType.JSON)
        .when()
        .get("event/all")
        .then()
        .statusCode(HttpStatus.OK.value())
        .body(Matchers.matchesPattern("\\[\\]"));
  }

  @Test
  @DataSet(value = "datasets/yml/events.yml", cleanBefore = true, cleanAfter = true)
  void testGetAllEvents() {
    RestAssured.given()
        .contentType(ContentType.JSON)
        .when()
        .get("event/all")
        .then()
        .statusCode(HttpStatus.OK.value())
        .body("title", Matchers.hasItems("Wargaming Fest", "ALEO main net opening"));
  }

  @Test
  @DataSet(value = "datasets/yml/events.yml", cleanBefore = true, cleanAfter = true)
  void testCreateNotExistEvent() {
    EventDto eventDto = new EventDto();
    eventDto.setEventId(14L);
    eventDto.setTitle("PokeCon");
    eventDto.setDescription("Big event with little pets from Pokemon universe.");
    eventDto.setEventDate(LocalDateTime.parse("2024-01-30T10:20:48"));
    eventDto.setLocation("Brest, Belarus");
    RestAssured.given()
        .contentType(ContentType.JSON)
        .given()
        .body(eventDto)
        .when()
        .post("/event")
        .then()
        .statusCode(HttpStatus.OK.value())
        .body("title", Matchers.equalTo("PokeCon"));
  }

  @Test
  @DataSet(value = "datasets/yml/events.yml", cleanBefore = true, cleanAfter = true)
  void testCreateExistEvent() {
    EventDto eventDto = new EventDto();
    eventDto.setEventId(505L);
    eventDto.setTitle("Wargaming Fest");
    eventDto.setDescription("A lot of steel!!!");
    eventDto.setEventDate(LocalDateTime.parse("2024-01-30T10:20:48"));
    eventDto.setLocation("Milan, Italy");
    RestAssured.given()
        .contentType(ContentType.JSON)
        .body(eventDto)
        .when()
        .post("/event")
        .then()
        .statusCode(HttpStatus.CONFLICT.value());
  }

  @Test
  @DataSet(value = "datasets/yml/events.yml", cleanBefore = true, cleanAfter = true)
  void getExistUserById() {
    RestAssured.given()
        .contentType(ContentType.JSON)
        .when()
        .get("/event/407")
        .then()
        .statusCode(HttpStatus.OK.value())
        .body("title", Matchers.equalTo("ALEO main net opening"))
        .body("description", Matchers.equalTo("Very interesting crypto event."));
  }
}
