package com.Eventhub.EventHubIntexSoft.service.Impl;

import com.Eventhub.EventHubIntexSoft.dto.ParticipantDto;
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
  private final ParticipantMapper participantMapper;

  public List<ParticipantDto> getAllParticipants() {
    return participantMapper.toDtoList(participantRepository.findAll());
  }

  public Optional<ParticipantDto> createParticipant(ParticipantDto participantDto) {
    return Optional.ofNullable(
        participantMapper.toParticipantDto(
            participantRepository.save(participantMapper.toParticipant(participantDto))));
  }

  public Optional<ParticipantDto> getParticipantByParticipantId(Long participantId) {
    return Optional.ofNullable(
        participantMapper.toParticipantDto(
            participantRepository.findParticipantByParticipantId(participantId)));
  }

  public Optional<ParticipantDto> updateParticipant(ParticipantDto participantDto) {
    // todo придумать, что будем обновлять.
    return Optional.ofNullable(
        participantMapper.toParticipantDto(
            participantRepository.findParticipantByParticipantId(
                participantDto.getParticipantId())));
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
