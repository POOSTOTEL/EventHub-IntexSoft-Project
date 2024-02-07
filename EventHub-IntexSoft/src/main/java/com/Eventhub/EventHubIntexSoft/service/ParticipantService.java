package com.Eventhub.EventHubIntexSoft.service;

import com.Eventhub.EventHubIntexSoft.dto.ParticipantDto;
import java.util.List;

public interface ParticipantService {
  List<ParticipantDto> getAllParticipants();

  ParticipantDto createParticipant(ParticipantDto participantDto);

  ParticipantDto getParticipantByParticipantId(Long participantId);

  ParticipantDto updateParticipant(ParticipantDto participantDto);

  ParticipantDto patchParticipant(ParticipantDto participantDto);

  void deleteParticipantByParticipantId(Long participantId);
}
