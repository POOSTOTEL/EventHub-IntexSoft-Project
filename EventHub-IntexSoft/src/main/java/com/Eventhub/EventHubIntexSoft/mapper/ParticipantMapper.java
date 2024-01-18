package com.Eventhub.EventHubIntexSoft.mapper;

import com.Eventhub.EventHubIntexSoft.DTO.ParticipantDTO;
import com.Eventhub.EventHubIntexSoft.entity.Participant;

public abstract class ParticipantMapper {
    public static Participant toParticipant (ParticipantDTO participantDTO) {
        return new Participant(participantDTO.getId(), UserMapper.toUser(participantDTO.getUserDTO()), EventMapper.toEvent(participantDTO.getEventDTO()));
    }
    public static ParticipantDTO toDTO (Participant participant) {
        return new ParticipantDTO(participant.getId(), UserMapper.toDTO(participant.getUser()), EventMapper.toDTO(participant.getEvent()));
    }
}
