package com.Eventhub.EventHubIntexSoft.integration.controller;

import com.Eventhub.EventHubIntexSoft.EventHubIntexSoftApplication;
import com.Eventhub.EventHubIntexSoft.dto.CommentDto;
import com.Eventhub.EventHubIntexSoft.integration.config.TestDatabaseConfiguration;
import com.Eventhub.EventHubIntexSoft.repository.CommentRepository;
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
public class CommentControllerIntegrationTest {
  @LocalServerPort protected Integer port;

  @BeforeEach
  void setUp() {
    RestAssured.baseURI = "http://localhost:" + port;
  }

  @Autowired CommentRepository commentRepository;

  @Test
  @DataSet(executeScriptsBefore = "scripts/truncateComments.sql")
  void testEmptyGetAllComments() {
    RestAssured.given()
        .contentType(ContentType.JSON)
        .when()
        .get("comment/all")
        .then()
        .statusCode(HttpStatus.OK.value())
        .body(Matchers.matchesPattern("\\[\\]"));
  }

  @Test
  @DataSet(
      value = "datasets/yml/comments.yml",
      executeScriptsBefore = "scripts/truncateComments.sql",
      executeScriptsAfter = "scripts/truncateComments.sql")
  void testGetAllComments() {
    RestAssured.given()
        .contentType(ContentType.JSON)
        .when()
        .get("comment/all")
        .then()
        .statusCode(HttpStatus.OK.value())
        .body("commentId", Matchers.hasItems(238, 106));
  }

  @Test
  @DataSet(
      value = "datasets/yml/comments.yml",
      executeScriptsBefore = "scripts/truncateComments.sql",
      executeScriptsAfter = "scripts/truncateComments.sql")
  void testCreateNotExistComment() {
    CommentDto commentDto = new CommentDto();
    commentDto.setCommentId(14L);
    commentDto.setUserId(104L);
    commentDto.setEventId(407L);
    commentDto.setComment("I work in crypto? Yes!");
    commentDto.setRating(8);
    commentDto.setCommentDate(LocalDateTime.parse("2024-01-30T10:20:48"));
    RestAssured.given()
        .contentType(ContentType.JSON)
        .given()
        .body(commentDto)
        .when()
        .post("/comment")
        .then()
        .statusCode(HttpStatus.OK.value())
        .body("userId", Matchers.equalTo(104))
        .body("eventId", Matchers.equalTo(407))
        .body("comment", Matchers.equalTo("I work in crypto? Yes!"));
  }

  @Test
  @DataSet(
      value = "datasets/yml/comments.yml",
      executeScriptsBefore = "scripts/truncateComments.sql",
      executeScriptsAfter = "scripts/truncateComments.sql")
  void testCreateExistComment() {
    CommentDto commentDto = new CommentDto();
    commentDto.setCommentId(238L);
    commentDto.setUserId(505L);
    commentDto.setEventId(321L);
    commentDto.setComment("I work in crypto? Yes!");
    commentDto.setRating(8);
    commentDto.setCommentDate(LocalDateTime.parse("2024-01-30T10:20:48"));
    RestAssured.given()
        .contentType(ContentType.JSON)
        .body(commentDto)
        .when()
        .post("/comment")
        .then()
        .statusCode(HttpStatus.CONFLICT.value());
  }

  @Test
  @DataSet(
      value = "datasets/yml/comments.yml",
      executeScriptsBefore = "scripts/truncateComments.sql",
      executeScriptsAfter = "scripts/truncateComments.sql")
  void getExistCommentById() {
    RestAssured.given()
        .contentType(ContentType.JSON)
        .when()
        .get("/comment/238")
        .then()
        .statusCode(HttpStatus.OK.value())
        .body("comment", Matchers.equalTo("Very interesting game event!"))
        .body("rating", Matchers.equalTo(10));
  }

  @Test
  @DataSet(
      value = "datasets/yml/comments.yml",
      executeScriptsBefore = "scripts/truncateComments.sql",
      executeScriptsAfter = "scripts/truncateComments.sql")
  void getNotExistCommentById() {
    RestAssured.given()
        .contentType(ContentType.JSON)
        .when()
        .get("/comment/320")
        .then()
        .statusCode(HttpStatus.NOT_FOUND.value());
  }

  @Test
  @DataSet(
      value = "datasets/yml/comments.yml",
      executeScriptsBefore = "scripts/truncateComments.sql",
      executeScriptsAfter = "scripts/truncateComments.sql")
  void updateExistCommentWithUniqData() {
    CommentDto commentDto = new CommentDto();
    commentDto.setCommentId(238L);
    commentDto.setUserId(null);
    commentDto.setEventId(null);
    commentDto.setComment("I work in crypto? Yes!");
    commentDto.setRating(8);
    commentDto.setCommentDate(null);
    RestAssured.given()
        .contentType(ContentType.JSON)
        .body(commentDto)
        .when()
        .put("/comment")
        .then()
        .statusCode(HttpStatus.OK.value())
        .body("commentId", Matchers.equalTo(238))
        .body("comment", Matchers.equalTo("I work in crypto? Yes!"))
        .body("rating", Matchers.equalTo(8))
        .body("commentDate", Matchers.equalTo("2024-01-31T10:24:48.583"));
  }

  @Test
  @DataSet(
      value = "datasets/yml/comments.yml",
      executeScriptsBefore = "scripts/truncateComments.sql",
      executeScriptsAfter = "scripts/truncateComments.sql")
  void updateNonExistComment() {
    CommentDto commentDto = new CommentDto();
    commentDto.setCommentId(404L);
    commentDto.setUserId(null);
    commentDto.setEventId(null);
    commentDto.setComment("I work in crypto? Yes!");
    commentDto.setRating(8);
    commentDto.setCommentDate(null);
    RestAssured.given()
        .contentType(ContentType.JSON)
        .body(commentDto)
        .when()
        .put("/comment")
        .then()
        .statusCode(HttpStatus.NOT_FOUND.value());
  }

  @Test
  @DataSet(
      value = "datasets/yml/comments.yml",
      executeScriptsBefore = "scripts/truncateComments.sql",
      executeScriptsAfter = "scripts/truncateComments.sql")
  void deleteExistComment() {
    RestAssured.given()
        .contentType(ContentType.JSON)
        .when()
        .delete("/comment/106")
        .then()
        .statusCode(HttpStatus.OK.value());
  }

  @Test
  @DataSet(
      value = "datasets/yml/comments.yml",
      executeScriptsBefore = "scripts/truncateComments.sql",
      executeScriptsAfter = "scripts/truncateComments.sql")
  void deleteNonExistComment() {
    RestAssured.given()
        .contentType(ContentType.JSON)
        .when()
        .delete("/comment/320")
        .then()
        .statusCode(HttpStatus.NOT_FOUND.value());
  }
}
