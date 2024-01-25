package com.Eventhub.EventHubIntexSoft.controller;

import com.Eventhub.EventHubIntexSoft.DTO.ParticipantDto;
import com.Eventhub.EventHubIntexSoft.entity.Participant;
import com.Eventhub.EventHubIntexSoft.service.Impl.ParticipantServiceImpl;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/participant")
public class ParticipantController {
  private final ParticipantServiceImpl participantServiceImpl;

  @GetMapping("/all")
  public ResponseEntity<List<ParticipantDto>> allParticipants() {
    return ResponseEntity.ok(participantServiceImpl.getAllParticipants());
  }

  @PostMapping
  public ResponseEntity<ParticipantDto> createParticipant(@RequestBody Participant participant) {
    return participantServiceImpl
        .createParticipant(participant)
        .map(participantDto -> new ResponseEntity<>(participantDto, HttpStatus.OK))
        .orElseGet(() -> new ResponseEntity<>(HttpStatus.CONFLICT));
  }

  @GetMapping("/{id}")
  public ResponseEntity<ParticipantDto> getParticipantById(@PathVariable("id") Long participantId) {
    return participantServiceImpl
        .getParticipantByParticipantId(participantId)
        .map(participantDto -> new ResponseEntity<>(participantDto, HttpStatus.OK))
        .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  @PutMapping
  public ResponseEntity<ParticipantDto> updateParticipant(
      @RequestBody ParticipantDto participantDto) {
    return participantServiceImpl
        .updateParticipant(participantDto)
        .map(participantDataTransferObject -> new ResponseEntity<>(participantDto, HttpStatus.OK))
        .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteParticipant(@PathVariable("id") Long participantId) {
    return participantServiceImpl.deleteParticipantByParticipantId(participantId)
        ? ResponseEntity.ok("Participant with id " + participantId + " deleted.")
        : new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }
}
