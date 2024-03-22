package com.Eventhub.EventHubIntexSoft.service.Impl;

import com.Eventhub.EventHubIntexSoft.dto.ParticipantDto;
import com.Eventhub.EventHubIntexSoft.entity.Participant;
import com.Eventhub.EventHubIntexSoft.mapper.ParticipantMapper;
import com.Eventhub.EventHubIntexSoft.repository.ParticipantRepository;
import com.Eventhub.EventHubIntexSoft.service.ParticipantService;
import java.beans.FeatureDescriptor;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ParticipantServiceImpl implements ParticipantService {
  private final ParticipantRepository participantRepository;
  private final ParticipantMapper participantMapper;

  public Page<ParticipantDto> getAllParticipants(Integer offset, Integer limit) {
    return participantRepository
        .findAll(PageRequest.of(offset, limit))
        .map(participantMapper::toParticipantDto);
  }

  public ParticipantDto createParticipant(ParticipantDto participantDto) {
    participantDto.setStatus("TRACKING");
    return participantMapper.toParticipantDto(
        participantRepository.save(participantMapper.toParticipant(participantDto)));
  }

  public ParticipantDto getParticipantByParticipantId(Long participantId) {
    return participantMapper.toParticipantDto(
        participantRepository.findParticipantByParticipantId(participantId));
  }

  public ParticipantDto updateParticipant(ParticipantDto participantDto) {
    Participant participant =
        participantRepository.findParticipantByParticipantId(participantDto.getParticipantId());
    BeanUtils.copyProperties(participantDto, participant, "participantId", "userId", "EventId");
    return participantMapper.toParticipantDto(participantRepository.save(participant));
  }

  public ParticipantDto patchParticipant(ParticipantDto participantDto) {
    Participant participant =
        participantRepository.findParticipantByParticipantId(participantDto.getParticipantId());
    BeanUtils.copyProperties(participantDto, participant, getIgnoreProperties(participantDto));
    return participantMapper.toParticipantDto(participantRepository.save(participant));
  }

  @Transactional(isolation = Isolation.READ_COMMITTED)
  public void deleteParticipantByParticipantId(Long participantId) {
    participantRepository.deleteParticipantByParticipantId(participantId);
  }

  private String[] getIgnoreProperties(ParticipantDto participantDto) {
    final BeanWrapper wrapper = new BeanWrapperImpl(participantDto);
    return Stream.of(wrapper.getPropertyDescriptors())
        .map(FeatureDescriptor::getName)
        // todo вырезать костыль в виде тройного ИЛИ
        .filter(
            name ->
                wrapper.getPropertyValue(name) == null
                    || wrapper.getPropertyValue(name) == "participantId"
                    || wrapper.getPropertyValue(name) == "userId"
                    || wrapper.getPropertyValue(name) == "eventId")
        .toArray(String[]::new);
  }
}
