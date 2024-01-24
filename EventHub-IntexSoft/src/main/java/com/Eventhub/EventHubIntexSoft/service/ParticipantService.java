package com.Eventhub.EventHubIntexSoft.service;

import com.Eventhub.EventHubIntexSoft.DTO.ParticipantDto;
import com.Eventhub.EventHubIntexSoft.entity.Participant;
import java.util.List;
import java.util.Optional;

public interface ParticipantService {
  List<ParticipantDto> getAllParticipants();

  Optional<ParticipantDto> createParticipant(Participant participant);

  Optional<ParticipantDto> getParticipantByParticipantId(Long participantId);

  Optional<ParticipantDto> updateParticipant(ParticipantDto participantDto);

  boolean deleteParticipantByParticipantId(Long participantId);
}
