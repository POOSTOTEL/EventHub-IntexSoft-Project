package com.Eventhub.EventHubIntexSoft.controller;

import com.Eventhub.EventHubIntexSoft.dto.EventDto;
import com.Eventhub.EventHubIntexSoft.exception.EmptyDtoFieldException;
import com.Eventhub.EventHubIntexSoft.exception.NonUniqValueException;
import com.Eventhub.EventHubIntexSoft.exception.NotFoundException;
import com.Eventhub.EventHubIntexSoft.service.EventService;
import com.Eventhub.EventHubIntexSoft.validator.EventValidator;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/event")
public class EventController {

  private final EventService eventService;
  private final EventValidator eventValidator;

  @GetMapping("/all")
  public ResponseEntity<List<EventDto>> allEvents() {
    return ResponseEntity.ok(eventService.getAllEvents());
  }

  @PostMapping
  public ResponseEntity<EventDto> createEvent(@RequestBody EventDto eventDto)
      throws EmptyDtoFieldException, NonUniqValueException {
    eventValidator.validateEventDtoSave(eventDto);
    return new ResponseEntity<>(eventService.createEvent(eventDto), HttpStatus.OK);
  }

  @GetMapping("/{eventId}")
  public ResponseEntity<EventDto> getEventByEventId(@PathVariable("eventId") Long eventId)
      throws NotFoundException {
    eventValidator.validateEventExistingByEventId(eventId);
    return new ResponseEntity<>(eventService.getEventByEventId(eventId), HttpStatus.OK);
  }

  @PutMapping
  public ResponseEntity<EventDto> updateEvent(@RequestBody EventDto eventDto)
      throws EmptyDtoFieldException, NotFoundException, NonUniqValueException {
    eventValidator.validateEventDtoUpdate(eventDto);
    return new ResponseEntity<>(eventService.updateEvent(eventDto), HttpStatus.OK);
  }

  @PatchMapping
  public ResponseEntity<EventDto> patchEvent(@RequestBody EventDto eventDto)
      throws EmptyDtoFieldException, NotFoundException, NonUniqValueException {
    eventValidator.validateEventDtoPatch(eventDto);
    return new ResponseEntity<>(eventService.patchEvent(eventDto), HttpStatus.OK);
  }

  @DeleteMapping("/{eventId}")
  public ResponseEntity<String> deleteEventByEventId(@PathVariable("eventId") Long eventId)
      throws NotFoundException {
    eventValidator.validateEventExistingByEventId(eventId);
    eventService.deleteEventByEventId(eventId);
    return ResponseEntity.ok("Event with id " + eventId + " deleted.");
  }
}
