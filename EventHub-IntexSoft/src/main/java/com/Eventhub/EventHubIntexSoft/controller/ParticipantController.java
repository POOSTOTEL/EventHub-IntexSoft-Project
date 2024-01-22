package com.Eventhub.EventHubIntexSoft.controller;

import com.Eventhub.EventHubIntexSoft.DTO.ParticipantDto;
import com.Eventhub.EventHubIntexSoft.entity.Participant;
import com.Eventhub.EventHubIntexSoft.service.ParticipantService;
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
@RequestMapping("/participant")
public class ParticipantController {
    private final ParticipantService participantService;

    @GetMapping("/all")
    public ResponseEntity<List<ParticipantDto>> allParticipants() {
        return ResponseEntity.ok(participantService.getAllParticipants());
    }

    @PostMapping("/create")
    public ResponseEntity<ParticipantDto> createParticipant(@RequestBody Participant participant) {
        return new ResponseEntity<>(participantService.createParticipant(participant).get(), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<ParticipantDto>> getParticipant(@PathVariable("id") Long id) {
        return ResponseEntity.ok(participantService.getParticipant(id));
    }

    @PostMapping("/update")
    public ResponseEntity<Optional<ParticipantDto>> updateParticipant(@RequestBody Participant participant) {
        return ResponseEntity.ok(participantService.updateParticipant(participant));
    }

    @GetMapping("/delete/{id}")
    public ResponseEntity<String> deleteParticipant(@PathVariable("id") Long id) {
        participantService.deleteParticipant(id);
        return ResponseEntity.ok("Participant with id " + id + " deleted.");
    }
}
