package com.Eventhub.EventHubIntexSoft.service;

import com.Eventhub.EventHubIntexSoft.dto.ParticipantDto;
import org.springframework.data.domain.Page;

public interface ParticipantService {
  Page<ParticipantDto> getAllParticipants(Integer offset, Integer limit);

  ParticipantDto createParticipant(ParticipantDto participantDto);

  ParticipantDto getParticipantByParticipantId(Long participantId);

  ParticipantDto updateParticipant(ParticipantDto participantDto);

  ParticipantDto patchParticipant(ParticipantDto participantDto);

  void deleteParticipantByParticipantId(Long participantId);
}
