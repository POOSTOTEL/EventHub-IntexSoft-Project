package com.Eventhub.EventHubIntexSoft.integration.controller;

import com.Eventhub.EventHubIntexSoft.EventHubIntexSoftApplication;
import com.Eventhub.EventHubIntexSoft.dto.ParticipantDto;
import com.Eventhub.EventHubIntexSoft.integration.config.TestDatabaseConfiguration;
import com.Eventhub.EventHubIntexSoft.repository.ParticipantRepository;
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
public class ParticipantControllerIntegrationTest {
  @LocalServerPort protected Integer port;

  @BeforeEach
  void setUp() {
    RestAssured.baseURI = "http://localhost:" + port;
  }

  @Autowired ParticipantRepository participantRepository;

  @Test
  @DataSet(executeScriptsBefore = "scripts/truncateParticipants.sql")
  void testEmptyGetAllParticipants() {
    RestAssured.given()
        .contentType(ContentType.JSON)
        .when()
        .get("participant/all")
        .then()
        .statusCode(HttpStatus.OK.value())
        .body(Matchers.matchesPattern("\\[\\]"));
  }

  @Test
  @DataSet(
      value = "datasets/yml/participants.yml",
      executeScriptsBefore = "scripts/truncateParticipants.sql",
      executeScriptsAfter = "scripts/truncateParticipants.sql")
  void testGetAllParticipants() {
    RestAssured.given()
        .contentType(ContentType.JSON)
        .when()
        .get("participant/all")
        .then()
        .statusCode(HttpStatus.OK.value())
        .body("participantId", Matchers.hasItems(728, 97));
  }

  @Test
  @DataSet(
      value = "datasets/yml/participants.yml",
      executeScriptsBefore = "scripts/truncateParticipants.sql",
      executeScriptsAfter = "scripts/truncateParticipants.sql")
  void testCreateNotExistParticipant() {
    ParticipantDto participantDto = new ParticipantDto();
    participantDto.setParticipantId(14L);
    participantDto.setUserId(104L);
    participantDto.setEventId(407L);
    RestAssured.given()
        .contentType(ContentType.JSON)
        .given()
        .body(participantDto)
        .when()
        .post("/participant")
        .then()
        .statusCode(HttpStatus.OK.value())
        .body("userId", Matchers.equalTo(104))
        .body("eventId", Matchers.equalTo(407));
  }

  @Test
  @DataSet(
      value = "datasets/yml/participants.yml",
      executeScriptsBefore = "scripts/truncateParticipants.sql",
      executeScriptsAfter = "scripts/truncateParticipants.sql")
  void testCreateExistParticipant() {
    ParticipantDto participantDto = new ParticipantDto();
    participantDto.setParticipantId(728L);
    participantDto.setUserId(505L);
    participantDto.setEventId(321L);
    RestAssured.given()
        .contentType(ContentType.JSON)
        .body(participantDto)
        .when()
        .post("/participant")
        .then()
        .statusCode(HttpStatus.CONFLICT.value());
  }

  @Test
  @DataSet(
      value = "datasets/yml/participants.yml",
      executeScriptsBefore = "scripts/truncateParticipants.sql",
      executeScriptsAfter = "scripts/truncateParticipants.sql")
  void getExistParticipantById() {
    RestAssured.given()
        .contentType(ContentType.JSON)
        .when()
        .get("/participant/97")
        .then()
        .statusCode(HttpStatus.OK.value())
        .body("eventId", Matchers.equalTo(505))
        .body("userId", Matchers.equalTo(104));
  }

  @Test
  @DataSet(
      value = "datasets/yml/participants.yml",
      executeScriptsBefore = "scripts/truncateParticipants.sql",
      executeScriptsAfter = "scripts/truncateParticipants.sql")
  void getNotExistParticipantById() {
    RestAssured.given()
        .contentType(ContentType.JSON)
        .when()
        .get("/participant/320")
        .then()
        .statusCode(HttpStatus.NOT_FOUND.value());
  }

  void updateNonExistParticipant() {
    ParticipantDto participantDto = new ParticipantDto();
    participantDto.setParticipantId(404L);
    participantDto.setUserId(null);
    participantDto.setEventId(null);
    RestAssured.given()
        .contentType(ContentType.JSON)
        .body(participantDto)
        .when()
        .put("/participant")
        .then()
        .statusCode(HttpStatus.NOT_FOUND.value());
  }

  @Test
  @DataSet(
      value = "datasets/yml/participants.yml",
      executeScriptsBefore = "scripts/truncateParticipants.sql",
      executeScriptsAfter = "scripts/truncateParticipants.sql")
  void deleteExistParticipant() {
    RestAssured.given()
        .contentType(ContentType.JSON)
        .when()
        .delete("/participant/728")
        .then()
        .statusCode(HttpStatus.OK.value());
  }

  @Test
  @DataSet(
      value = "datasets/yml/participants.yml",
      executeScriptsBefore = "scripts/truncateParticipants.sql",
      executeScriptsAfter = "scripts/truncateParticipants.sql")
  void deleteNonExistParticipant() {
    RestAssured.given()
        .contentType(ContentType.JSON)
        .when()
        .delete("/participant/320")
        .then()
        .statusCode(HttpStatus.NOT_FOUND.value());
  }
}
