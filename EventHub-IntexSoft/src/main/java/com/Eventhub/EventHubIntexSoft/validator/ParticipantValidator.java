package com.Eventhub.EventHubIntexSoft.validator;

import com.Eventhub.EventHubIntexSoft.dto.ParticipantDto;
import com.Eventhub.EventHubIntexSoft.entity.Participant;
import com.Eventhub.EventHubIntexSoft.entity.ParticipantStatus;
import com.Eventhub.EventHubIntexSoft.exception.EmptyDtoFieldException;
import com.Eventhub.EventHubIntexSoft.exception.FormatException;
import com.Eventhub.EventHubIntexSoft.exception.NonUniqValueException;
import com.Eventhub.EventHubIntexSoft.exception.NotFoundException;
import com.Eventhub.EventHubIntexSoft.repository.EventRepository;
import com.Eventhub.EventHubIntexSoft.repository.ParticipantRepository;
import com.Eventhub.EventHubIntexSoft.repository.UserRepository;
import java.util.Objects;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ParticipantValidator extends FieldValidator {
  @Autowired protected ParticipantRepository participantRepository;
  @Autowired protected UserRepository userRepository;
  @Autowired protected EventRepository eventRepository;

  public void validateUserId(Long userId) throws EmptyDtoFieldException, NotFoundException {
    validateNullField(userId);
    validateUserExistingByUserId(userId);
  }

  public void validateEventId(Long eventId) throws EmptyDtoFieldException, NotFoundException {
    validateNullField(eventId);
    validateEventExistingByEventId(eventId);
  }

  public void validateStatus(String status, Long participantId)
      throws FormatException, EmptyDtoFieldException {
    validateNullField(status);
    validateStatusHierarchy(status, participantId);
  }

  public void validateParticipantDtoSave(ParticipantDto participantDto)
      throws EmptyDtoFieldException, NotFoundException, NonUniqValueException {
    validateUserId(participantDto.getUserId());
    validateEventId(participantDto.getEventId());
    validateUniqParticipant(participantDto.getUserId(), participantDto.getEventId());
  }

  public void validateParticipantDtoUpdate(ParticipantDto participantDto)
      throws NotFoundException, EmptyDtoFieldException, NonUniqValueException {
    validateParticipantExistingByParticipantId(participantDto.getParticipantId());
    validateParticipantDtoSave(participantDto);
  }

  public void validateParticipantDtoPatch(ParticipantDto participantDto)
      throws NotFoundException, EmptyDtoFieldException, FormatException {
    validateParticipantExistingByParticipantId(participantDto.getParticipantId());
    if (Objects.nonNull(participantDto.getUserId())) {
      validateUserId(participantDto.getUserId());
    }
    if (Objects.nonNull(participantDto.getEventId())) {
      validateEventId(participantDto.getEventId());
    }
    if (Objects.nonNull(participantDto.getStatus())) {
      validateStatus(participantDto.getStatus(), participantDto.getParticipantId());
    }
  }

  public void validateUniqParticipant(Long userId, Long eventId) throws NonUniqValueException {
    if (Objects.nonNull(
        participantRepository.findParticipantByUser_UserIdAndEvent_EventId(userId, eventId))) {
      throw new NonUniqValueException();
    }
  }

  public void validateParticipantExistingByParticipantId(Long participantId)
      throws NotFoundException {
    if (Objects.isNull(participantRepository.findParticipantByParticipantId(participantId))) {
      throw new NotFoundException();
    }
  }

  public void validateUserExistingByUserId(Long userId) throws NotFoundException {
    if (Objects.isNull(userRepository.findUserByUserId(userId))) {
      throw new NotFoundException();
    }
  }

  public void validateEventExistingByEventId(Long eventId) throws NotFoundException {
    if (Objects.isNull(eventRepository.findEventByEventId(eventId))) {
      throw new NotFoundException();
    }
  }

  public void validateStatusHierarchy(String status, Long participantId) throws FormatException {
    Optional<Participant> participant =
        Optional.ofNullable(participantRepository.findParticipantByParticipantId(participantId));
    if (participant.isPresent()) {
      ParticipantStatus statusOfExistParticipant = participant.get().getStatus();
      if (statusOfExistParticipant.getStatusId() > 1
          || !status.equals("MISSED")) {
        throw new FormatException();
      }
    } else {
      if (!status.equals("TRACKING")) {
        throw new FormatException();
      }
    }
  }
}
