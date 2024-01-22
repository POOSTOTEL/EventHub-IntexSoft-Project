package com.Eventhub.EventHubIntexSoft.controller;

import com.Eventhub.EventHubIntexSoft.DTO.EventDto;
import com.Eventhub.EventHubIntexSoft.entity.Event;
import com.Eventhub.EventHubIntexSoft.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/event")
public class EventController {
    private final EventService eventService;

    @GetMapping("/all")
    public ResponseEntity<List<EventDto>> allEvents() {
        return ResponseEntity.ok(eventService.getAllEvents());
    }

    @PostMapping("/create")
    public ResponseEntity<EventDto> createEvent(@RequestBody Event event) {
        return new ResponseEntity<>(eventService.createEvent(event).get(), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<EventDto>> getEvent(@PathVariable("id") Long id) {
        return ResponseEntity.ok(eventService.getEvent(id));
    }

    @PostMapping("/update")
    public ResponseEntity<Optional<EventDto>> updateEvent(@RequestBody Event event) {
        return ResponseEntity.ok(eventService.updateEvent(event));
    }

    @GetMapping("/delete/{id}")
    public ResponseEntity<String> deleteEvent(@PathVariable("id") Long id) {
        eventService.deleteEvent(id);
        return ResponseEntity.ok("Event with id " + id + " deleted.");
    }
}
