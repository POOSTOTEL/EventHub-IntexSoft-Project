package com.Eventhub.EventHubIntexSoft.service;

import com.Eventhub.EventHubIntexSoft.dto.ParticipantDto;
import java.util.List;
import java.util.Optional;

public interface ParticipantService {
  List<ParticipantDto> getAllParticipants();

  Optional<ParticipantDto> createParticipant(ParticipantDto participantDto);

  Optional<ParticipantDto> getParticipantByParticipantId(Long participantId);

  Optional<ParticipantDto> updateParticipant(ParticipantDto participantDto);

  boolean deleteParticipantByParticipantId(Long participantId);
}
