package com.Eventhub.EventHubIntexSoft.controller;

import com.Eventhub.EventHubIntexSoft.dto.ParticipantDto;
import com.Eventhub.EventHubIntexSoft.exception.EmptyDtoFieldException;
import com.Eventhub.EventHubIntexSoft.exception.FormatException;
import com.Eventhub.EventHubIntexSoft.exception.NonUniqValueException;
import com.Eventhub.EventHubIntexSoft.exception.NotFoundException;
import com.Eventhub.EventHubIntexSoft.service.ParticipantService;
import com.Eventhub.EventHubIntexSoft.validator.ParticipantValidator;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/participant")
public class ParticipantController {
  private final ParticipantService participantService;
  private final ParticipantValidator participantValidator;

  @GetMapping("/all")
  public ResponseEntity<Page<ParticipantDto>> allParticipants(
      @RequestParam(value = "offset", defaultValue = "0") @Min(0) Integer offset,
      @RequestParam(value = "limit", defaultValue = "5") @Min(1) @Max(100) Integer limit) {
    return ResponseEntity.ok(participantService.getAllParticipants(offset, limit));
  }

  @PostMapping
  public ResponseEntity<ParticipantDto> createParticipant(
      @RequestBody ParticipantDto participantDto)
      throws EmptyDtoFieldException, NotFoundException, NonUniqValueException {
    participantValidator.validateParticipantDtoSave(participantDto);
    return new ResponseEntity<>(
        participantService.createParticipant(participantDto), HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<ParticipantDto> getParticipantByParticipantId(
      @PathVariable("id") Long participantId) throws NotFoundException {
    participantValidator.validateParticipantExistingByParticipantId(participantId);
    return new ResponseEntity<>(
        participantService.getParticipantByParticipantId(participantId), HttpStatus.OK);
  }

  @PutMapping
  public ResponseEntity<ParticipantDto> updateParticipant(
      @RequestBody ParticipantDto participantDto)
      throws EmptyDtoFieldException, NotFoundException, NonUniqValueException {
    participantValidator.validateParticipantDtoUpdate(participantDto);
    return new ResponseEntity<>(
        participantService.updateParticipant(participantDto), HttpStatus.OK);
  }

  @PatchMapping
  public ResponseEntity<ParticipantDto> patchParticipant(@RequestBody ParticipantDto participantDto)
      throws EmptyDtoFieldException, NotFoundException, FormatException {
    participantValidator.validateParticipantDtoPatch(participantDto);
    participantService.patchParticipant(participantDto);
    return new ResponseEntity<>(
        participantService.getParticipantByParticipantId(participantDto.getParticipantId()),
        HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteParticipantByParticipantId(
      @PathVariable("id") Long participantId) throws NotFoundException {
    participantValidator.validateParticipantExistingByParticipantId(participantId);
    participantService.deleteParticipantByParticipantId(participantId);
    return ResponseEntity.ok("Participant with id " + participantId + " deleted.");
  }
}
