package com.Eventhub.EventHubIntexSoft.integration.controller;

import com.Eventhub.EventHubIntexSoft.EventHubIntexSoftApplication;
import com.Eventhub.EventHubIntexSoft.dto.EventDto;
import com.Eventhub.EventHubIntexSoft.integration.config.TestDatabaseConfiguration;
import com.Eventhub.EventHubIntexSoft.repository.EventRepository;
import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.configuration.Orthography;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.junit5.api.DBRider;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.time.LocalDateTime;
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
public class EventControllerIntegrationTest {

  @LocalServerPort protected Integer port;

  @BeforeEach
  void setUp() {
    RestAssured.baseURI = "http://localhost:" + port;
  }

  @Autowired EventRepository eventRepository;

  @Test
  @DataSet(executeScriptsBefore = "scripts/truncateEvents.sql")
  void testEmptyGetAllEvents() {
    RestAssured.given()
        .contentType(ContentType.JSON)
        .when()
        .get("event/all")
        .then()
        .statusCode(HttpStatus.OK.value())
        .body(Matchers.matchesPattern("\\[\\]"));
  }

  @Test
  @DataSet(
      value = "datasets/yml/events.yml",
      executeScriptsBefore = "scripts/truncateEvents.sql",
      executeScriptsAfter = "scripts/truncateEvents.sql")
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
  @DataSet(
      value = "datasets/yml/events.yml",
      executeScriptsBefore = "scripts/truncateEvents.sql",
      executeScriptsAfter = "scripts/truncateEvents.sql")
  void testCreateNotExistEvent() {
    EventDto eventDto = new EventDto();
    eventDto.setEventId(null);
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
  @DataSet(
      value = "datasets/yml/events.yml",
      executeScriptsBefore = "scripts/truncateEvents.sql",
      executeScriptsAfter = "scripts/truncateEvents.sql")
  void testCreateExistEvent() {
    EventDto eventDto = new EventDto();
    eventDto.setEventId(null);
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
        .statusCode(HttpStatus.NOT_ACCEPTABLE.value());
  }

  @Test
  @DataSet(
      value = "datasets/yml/events.yml",
      executeScriptsBefore = "scripts/truncateEvents.sql",
      executeScriptsAfter = "scripts/truncateEvents.sql")
  void getExistEventById() {
    RestAssured.given()
        .contentType(ContentType.JSON)
        .when()
        .get("/event/407")
        .then()
        .statusCode(HttpStatus.OK.value())
        .body("title", Matchers.equalTo("ALEO main net opening"))
        .body("description", Matchers.equalTo("Very interesting crypto event."));
  }

  @Test
  @DataSet(
      value = "datasets/yml/events.yml",
      executeScriptsBefore = "scripts/truncateEvents.sql",
      executeScriptsAfter = "scripts/truncateEvents.sql")
  void getNotExistEventById() {
    RestAssured.given()
        .contentType(ContentType.JSON)
        .when()
        .get("/event/320")
        .then()
        .statusCode(HttpStatus.NOT_FOUND.value());
  }

  @Test
  @DataSet(
      value = "datasets/yml/events.yml",
      executeScriptsBefore = "scripts/truncateEvents.sql",
      executeScriptsAfter = "scripts/truncateEvents.sql")
  void updateExistEventWithUniqData() {
    EventDto eventDto = new EventDto();
    eventDto.setEventId(505L);
    eventDto.setTitle("Wargaming Fest");
    eventDto.setDescription("Kipr summer festival.");
    eventDto.setEventDate(LocalDateTime.parse("2024-01-30T10:20:48"));
    eventDto.setLocation("Warshava, Poland");
    RestAssured.given()
        .contentType(ContentType.JSON)
        .body(eventDto)
        .when()
        .put("/event")
        .then()
        .statusCode(HttpStatus.OK.value())
        .body("eventId", Matchers.equalTo(505))
        .body("title", Matchers.equalTo("Wargaming Fest"))
        .body("description", Matchers.equalTo("Kipr summer festival."))
        .body("location", Matchers.equalTo("Warshava, Poland"));
  }

  @Test
  @DataSet(
      value = "datasets/yml/events.yml",
      executeScriptsBefore = "scripts/truncateEvents.sql",
      executeScriptsAfter = "scripts/truncateEvents.sql")
  void updateExistEventWithNonUniqData() {
    EventDto eventDto = new EventDto();
    eventDto.setEventId(407L);
    eventDto.setTitle("Wargaming Fest");
    eventDto.setDescription("Big event with little pets from Pokemon universe.");
    eventDto.setEventDate(LocalDateTime.parse("2024-01-30T10:20:48"));
    eventDto.setLocation("Brest, Belarus");
    RestAssured.given()
        .contentType(ContentType.JSON)
        .body(eventDto)
        .when()
        .put("/event")
        .then()
        .statusCode(HttpStatus.NOT_ACCEPTABLE.value());
  }

  @Test
  @DataSet(
      value = "datasets/yml/events.yml",
      executeScriptsBefore = "scripts/truncateEvents.sql",
      executeScriptsAfter = "scripts/truncateEvents.sql")
  void updateNonExistEvent() {
    EventDto eventDto = new EventDto();
    eventDto.setEventId(402L);
    eventDto.setTitle("Wargaming Fest");
    eventDto.setDescription("A lot of steel!!!");
    eventDto.setEventDate(LocalDateTime.parse("2024-01-30T10:20:48"));
    eventDto.setLocation("Milan, Italy");
    RestAssured.given()
        .contentType(ContentType.JSON)
        .body(eventDto)
        .when()
        .put("/event")
        .then()
        .statusCode(HttpStatus.NOT_FOUND.value());
  }
  @Test
  @DataSet(
          value = "datasets/yml/events.yml",
          executeScriptsBefore = "scripts/truncateEvents.sql",
          executeScriptsAfter = "scripts/truncateEvents.sql")
  void patchExistEventWithUniqData() {
    EventDto eventDto = new EventDto();
    eventDto.setEventId(505L);
    eventDto.setTitle(null);
    eventDto.setDescription("Kipr summer festival.");
    eventDto.setEventDate(null);
    eventDto.setLocation("Warshava, Poland");
    RestAssured.given()
            .contentType(ContentType.JSON)
            .body(eventDto)
            .when()
            .patch("/event")
            .then()
            .statusCode(HttpStatus.OK.value())
            .body("eventId", Matchers.equalTo(505))
            .body("title", Matchers.equalTo("Wargaming Fest"))
            .body("description", Matchers.equalTo("Kipr summer festival."))
            .body("location", Matchers.equalTo("Warshava, Poland"));
  }

  @Test
  @DataSet(
          value = "datasets/yml/events.yml",
          executeScriptsBefore = "scripts/truncateEvents.sql",
          executeScriptsAfter = "scripts/truncateEvents.sql")
  void patchExistEventWithNonUniqData() {
    EventDto eventDto = new EventDto();
    eventDto.setEventId(407L);
    eventDto.setTitle("Wargaming Fest");
    eventDto.setDescription("Big event with little pets from Pokemon universe.");
    eventDto.setEventDate(null);
    eventDto.setLocation(null);
    RestAssured.given()
            .contentType(ContentType.JSON)
            .body(eventDto)
            .when()
            .patch("/event")
            .then()
            .statusCode(HttpStatus.NOT_ACCEPTABLE.value());
  }

  @Test
  @DataSet(
          value = "datasets/yml/events.yml",
          executeScriptsBefore = "scripts/truncateEvents.sql",
          executeScriptsAfter = "scripts/truncateEvents.sql")
  void patchNonExistEvent() {
    EventDto eventDto = new EventDto();
    eventDto.setEventId(402L);
    eventDto.setTitle(null);
    eventDto.setDescription("A lot of steel!!!");
    eventDto.setEventDate(null);
    eventDto.setLocation("Milan, Italy");
    RestAssured.given()
            .contentType(ContentType.JSON)
            .body(eventDto)
            .when()
            .patch("/event")
            .then()
            .statusCode(HttpStatus.NOT_FOUND.value());
  }
  @Test
  @DataSet(
      value = "datasets/yml/events.yml",
      executeScriptsBefore = "scripts/truncateEvents.sql",
      executeScriptsAfter = "scripts/truncateEvents.sql")
  void deleteExistEvent() {
    RestAssured.given()
        .contentType(ContentType.JSON)
        .when()
        .delete("/event/505")
        .then()
        .statusCode(HttpStatus.OK.value());
  }

  @Test
  @DataSet(
      value = "datasets/yml/events.yml",
      executeScriptsBefore = "scripts/truncateEvents.sql",
      executeScriptsAfter = "scripts/truncateEvents.sql")
  void deleteNonExistEvent() {
    RestAssured.given()
        .contentType(ContentType.JSON)
        .when()
        .delete("/event/320")
        .then()
        .statusCode(HttpStatus.NOT_FOUND.value());
  }
}
