package com.Eventhub.EventHubIntexSoft.controller;

import com.Eventhub.EventHubIntexSoft.dto.ParticipantDto;
import com.Eventhub.EventHubIntexSoft.entity.Participant;
import com.Eventhub.EventHubIntexSoft.service.ParticipantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(
    name = "ParticipantController",
    description = "Provides CRUD functionality for the Participant entity")
@RestController
@RequiredArgsConstructor
@RequestMapping("/participant")
public class ParticipantController {
  private final ParticipantService participantService;

  @GetMapping("/all")
  @Operation(
      summary = "Get all participants",
      description = "Fetch all participants and return them in a list.")
  @Parameters()
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Successful operation",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ParticipantDto.class))
            })
      })
  public ResponseEntity<List<ParticipantDto>> allParticipants() {
    return ResponseEntity.ok(participantService.getAllParticipants());
  }

  @PostMapping
  @Operation(
      summary = "Create a new participant",
      description =
          "Create a new participant and return the created participant if successful, or a conflict status if not successful.")
  @Parameters({
    @Parameter(
        name = "participant",
        required = true,
        description = "Participant to be created",
        schema = @Schema(implementation = Participant.class))
  })
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Participant created successfully",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ParticipantDto.class))
            }),
        @ApiResponse(
            responseCode = "409",
            description = "Participant creation failed due to conflict",
            content = @Content)
      })
  public ResponseEntity<ParticipantDto> createParticipant(@RequestBody Participant participant) {
    return participantService
        .createParticipant(participant)
        .map(participantDto -> new ResponseEntity<>(participantDto, HttpStatus.OK))
        .orElseGet(() -> new ResponseEntity<>(HttpStatus.CONFLICT));
  }

  @GetMapping("/{id}")
  @Operation(
      summary = "Get a participant by ID",
      description =
          "Fetch a participant by their ID and return the participant if found, or a not found status if not found.")
  @Parameters({
    @Parameter(
        name = "id",
        required = true,
        description = "ID of the participant to be fetched",
        schema = @Schema(type = "integer", format = "int64"))
  })
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Participant found successfully",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ParticipantDto.class))
            }),
        @ApiResponse(
            responseCode = "404",
            description = "Participant not found",
            content = @Content)
      })
  public ResponseEntity<ParticipantDto> getParticipantByParticipantId(
      @PathVariable("id") Long participantId) {
    return participantService
        .getParticipantByParticipantId(participantId)
        .map(participantDto -> new ResponseEntity<>(participantDto, HttpStatus.OK))
        .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  @PutMapping
  @Operation(
      summary = "Update a participant",
      description =
          "Update a participant and return the updated participant if successful, or a not found status if not successful.")
  @Parameters({
    @Parameter(
        name = "participantDto",
        required = true,
        description = "ParticipantDto to be updated",
        schema = @Schema(implementation = ParticipantDto.class))
  })
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Participant updated successfully",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ParticipantDto.class))
            }),
        @ApiResponse(
            responseCode = "404",
            description = "Participant not found",
            content = @Content)
      })
  public ResponseEntity<ParticipantDto> updateParticipant(
      @RequestBody ParticipantDto participantDto) {
    return participantService
        .updateParticipant(participantDto)
        .map(participantDataTransferObject -> new ResponseEntity<>(participantDto, HttpStatus.OK))
        .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  @DeleteMapping("/{id}")
  @Operation(
      summary = "Delete a participant by ID",
      description =
          "Delete a participant by their ID and return a success message if successful, or a not found status if not successful.")
  @Parameters({
    @Parameter(
        name = "id",
        required = true,
        description = "ID of the participant to be deleted",
        schema = @Schema(type = "integer", format = "int64"))
  })
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Participant deleted successfully",
            content = {@Content(mediaType = "text/plain", schema = @Schema(type = "string"))}),
        @ApiResponse(
            responseCode = "404",
            description = "Participant not found",
            content = @Content)
      })
  public ResponseEntity<String> deleteParticipantByParticipantId(
      @PathVariable("id") Long participantId) {
    return participantService.deleteParticipantByParticipantId(participantId)
        ? ResponseEntity.ok("Participant with id " + participantId + " deleted.")
        : new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }
}
