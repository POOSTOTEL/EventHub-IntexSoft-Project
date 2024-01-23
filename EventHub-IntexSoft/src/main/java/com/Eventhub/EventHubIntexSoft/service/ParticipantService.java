package com.Eventhub.EventHubIntexSoft.service;

import com.Eventhub.EventHubIntexSoft.DTO.ParticipantDto;
import com.Eventhub.EventHubIntexSoft.entity.Participant;
import java.util.List;
import java.util.Optional;

public interface ParticipantService {
  List<ParticipantDto> getAllParticipants();

  Optional<ParticipantDto> createParticipant(Participant participant);

  Optional<ParticipantDto> getParticipantById(Long id);

  Optional<ParticipantDto> updateParticipant(ParticipantDto participantDto);

  boolean deleteParticipantById(Long id);
}
