package com.Eventhub.EventHubIntexSoft.mapper;

import com.Eventhub.EventHubIntexSoft.DTO.ParticipantDto;
import com.Eventhub.EventHubIntexSoft.entity.Participant;
import org.mapstruct.Mapper;

import static org.mapstruct.factory.Mappers.getMapper;

@Mapper(componentModel = "spring")
public interface ParticipantMapper {
    ParticipantMapper instance = getMapper(ParticipantMapper.class);

    Participant toParticipant(ParticipantDto participantDto);

    ParticipantDto toParticipantDto(Participant participant);
}
