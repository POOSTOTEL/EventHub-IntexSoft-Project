package com.Eventhub.EventHubIntexSoft.service;

import com.Eventhub.EventHubIntexSoft.DTO.ParticipantDto;
import com.Eventhub.EventHubIntexSoft.entity.Participant;
import com.Eventhub.EventHubIntexSoft.mapper.ParticipantListMapper;
import com.Eventhub.EventHubIntexSoft.mapper.ParticipantMapper;
import com.Eventhub.EventHubIntexSoft.repository.ParticipantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Service
@RequiredArgsConstructor
public class ParticipantService {
    private final ParticipantRepository participantRepository;
    public List<ParticipantDto> getAllParticipants() {return ParticipantListMapper.instance.toDtoList(participantRepository.findAll());}
    public Optional<ParticipantDto> createParticipant(Participant participant) {
        return Optional.ofNullable(ParticipantMapper.instance.toParticipantDto(participantRepository.save(participant)));
    }
    public Optional<ParticipantDto> getParticipant(Long id) {
        return Optional.ofNullable(ParticipantMapper.instance.toParticipantDto(participantRepository.findParticipantById(id)));
    }
    public Optional<ParticipantDto> updateParticipant(Participant participant) {
        Participant participantFromDB = participantRepository.findParticipantById(participant.getId());
        //todo придумать, что будем обновлять.
        return Optional.of(ParticipantMapper.instance.toParticipantDto(participantRepository.save(participantFromDB)));
    }
    @Transactional
    public void deleteParticipant(Long id) {
        participantRepository.deleteParticipantById(id);
    }
}
