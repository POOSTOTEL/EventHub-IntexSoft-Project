package com.Eventhub.EventHubIntexSoft.service.Impl;

import com.Eventhub.EventHubIntexSoft.DTO.ParticipantDto;
import com.Eventhub.EventHubIntexSoft.entity.Participant;
import com.Eventhub.EventHubIntexSoft.mapper.ParticipantListMapper;
import com.Eventhub.EventHubIntexSoft.mapper.ParticipantMapper;
import com.Eventhub.EventHubIntexSoft.repository.ParticipantRepository;
import com.Eventhub.EventHubIntexSoft.service.ParticipantService;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ParticipantServiceImpl implements ParticipantService {
  private final ParticipantRepository participantRepository;

  public List<ParticipantDto> getAllParticipants() {
    return ParticipantListMapper.instance.toDtoList(participantRepository.findAll());
  }

  public Optional<ParticipantDto> createParticipant(Participant participant) {
    return Optional.ofNullable(
        ParticipantMapper.instance.toParticipantDto(participantRepository.save(participant)));
  }

  public Optional<ParticipantDto> getParticipantByParticipantId(Long participantId) {
    return Optional.ofNullable(
        ParticipantMapper.instance.toParticipantDto(participantRepository.findParticipantByParticipantId(participantId)));
  }

  public Optional<ParticipantDto> updateParticipant(ParticipantDto participantDto) {
    // todo придумать, что будем обновлять.
    return Optional.ofNullable(
        ParticipantMapper.instance.toParticipantDto(
            participantRepository.findParticipantByParticipantId(participantDto.getParticipantId())));
  }

  @Transactional(isolation = Isolation.READ_COMMITTED)
  public boolean deleteParticipantByParticipantId(Long participantId) {
    return Optional.ofNullable(participantRepository.findParticipantByParticipantId(participantId))
        .map(
            user -> {
              participantRepository.deleteParticipantByParticipantId(participantId);
              return true;
            })
        .orElse(false);
  }
}
