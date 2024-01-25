package com.Eventhub.EventHubIntexSoft.controller;

import com.Eventhub.EventHubIntexSoft.DTO.EventDto;
import com.Eventhub.EventHubIntexSoft.entity.Event;
import com.Eventhub.EventHubIntexSoft.service.Impl.EventServiceImpl;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/event")
public class EventController {
  private final EventServiceImpl eventServiceImpl;

  @GetMapping("/all")
  public ResponseEntity<List<EventDto>> allEvents() {
    return ResponseEntity.ok(eventServiceImpl.getAllEvents());
  }

  @PostMapping
  public ResponseEntity<EventDto> createEvent(@RequestBody Event event) {
    return eventServiceImpl
        .createEvent(event)
        .map(eventDto -> new ResponseEntity<>(eventDto, HttpStatus.OK))
        .orElseGet(() -> new ResponseEntity<>(HttpStatus.CONFLICT));
  }

  @GetMapping("/{id}")
  public ResponseEntity<EventDto> getEventById(@PathVariable("id") Long eventId) {
    return eventServiceImpl
        .getEventByEventId(eventId)
        .map(eventDto -> new ResponseEntity<>(eventDto, HttpStatus.OK))
        .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  @PutMapping
  public ResponseEntity<EventDto> updateEvent(@RequestBody EventDto eventDto) {
    return eventServiceImpl
        .updateEvent(eventDto)
        .map(
            eventDataTransferObject -> new ResponseEntity<>(eventDataTransferObject, HttpStatus.OK))
        .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteEvent(@PathVariable("id") Long eventId) {
    return eventServiceImpl.deleteEventByEventId(eventId)
        ? ResponseEntity.ok("Event with id " + eventId + " deleted.")
        : new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }
}
