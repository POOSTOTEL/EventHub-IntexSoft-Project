package com.Eventhub.EventHubIntexSoft.controller;

import com.Eventhub.EventHubIntexSoft.DTO.EventDto;
import com.Eventhub.EventHubIntexSoft.entity.Event;
import com.Eventhub.EventHubIntexSoft.service.Impl.EventServiceImpl;
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

@Tag(name = "EventController", description = "Provides CRUD functionality for the Event entity")
@RestController
@RequiredArgsConstructor
@RequestMapping("/event")
public class EventController {
  private final EventServiceImpl eventServiceImpl;

  @GetMapping("/all")
  @Operation(
      summary = "Get all events",
      description = "Fetch all events and return them in a list.")
  @Parameters()
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Successful operation",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = EventDto.class))
            })
      })
  public ResponseEntity<List<EventDto>> allEvents() {
    return ResponseEntity.ok(eventServiceImpl.getAllEvents());
  }

  @PostMapping
  @Operation(
      summary = "Create a new event",
      description =
          "Create a new event and return the created event if successful, or a conflict status if not successful.")
  @Parameters({
    @Parameter(
        name = "event",
        required = true,
        description = "Event to be created",
        schema = @Schema(implementation = Event.class))
  })
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Event created successfully",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = EventDto.class))
            }),
        @ApiResponse(
            responseCode = "409",
            description = "Event creation failed due to conflict",
            content = @Content)
      })
  public ResponseEntity<EventDto> createEvent(@RequestBody Event event) {
    return eventServiceImpl
        .createEvent(event)
        .map(eventDto -> new ResponseEntity<>(eventDto, HttpStatus.OK))
        .orElseGet(() -> new ResponseEntity<>(HttpStatus.CONFLICT));
  }

  @GetMapping("/{id}")
  @Operation(
      summary = "Get an event by ID",
      description =
          "Fetch an event by their ID and return the event if found, or a not found status if not found.")
  @Parameters({
    @Parameter(
        name = "id",
        required = true,
        description = "ID of the event to be fetched",
        schema = @Schema(type = "integer", format = "int64"))
  })
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Event found successfully",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = EventDto.class))
            }),
        @ApiResponse(responseCode = "404", description = "Event not found", content = @Content)
      })
  public ResponseEntity<EventDto> getEventById(@PathVariable("id") Long eventId) {
    return eventServiceImpl
        .getEventByEventId(eventId)
        .map(eventDto -> new ResponseEntity<>(eventDto, HttpStatus.OK))
        .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  @PutMapping
  @Operation(
      summary = "Update an event",
      description =
          "Update an event and return the updated event if successful, or a not found status if not successful.")
  @Parameters({
    @Parameter(
        name = "eventDto",
        required = true,
        description = "EventDto to be updated",
        schema = @Schema(implementation = EventDto.class))
  })
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Event updated successfully",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = EventDto.class))
            }),
        @ApiResponse(responseCode = "404", description = "Event not found", content = @Content)
      })
  public ResponseEntity<EventDto> updateEvent(@RequestBody EventDto eventDto) {
    return eventServiceImpl
        .updateEvent(eventDto)
        .map(
            eventDataTransferObject -> new ResponseEntity<>(eventDataTransferObject, HttpStatus.OK))
        .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  @DeleteMapping("/{id}")
  @Operation(
      summary = "Delete an event by ID",
      description =
          "Delete an event by their ID and return a success message if successful, or a not found status if not successful.")
  @Parameters({
    @Parameter(
        name = "id",
        required = true,
        description = "ID of the event to be deleted",
        schema = @Schema(type = "integer", format = "int64"))
  })
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Event deleted successfully",
            content = {@Content(mediaType = "text/plain", schema = @Schema(type = "string"))}),
        @ApiResponse(responseCode = "404", description = "Event not found", content = @Content)
      })
  public ResponseEntity<String> deleteEvent(@PathVariable("id") Long eventId) {
    return eventServiceImpl.deleteEventByEventId(eventId)
        ? ResponseEntity.ok("Event with id " + eventId + " deleted.")
        : new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }
}
