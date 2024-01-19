package com.Eventhub.EventHubIntexSoft.mapper;

import com.Eventhub.EventHubIntexSoft.DTO.EventDto;
import com.Eventhub.EventHubIntexSoft.DTO.ParticipantDto;
import com.Eventhub.EventHubIntexSoft.entity.Event;
import com.Eventhub.EventHubIntexSoft.entity.Participant;
import org.mapstruct.Mapper;

import java.util.List;

import static org.mapstruct.factory.Mappers.getMapper;

@Mapper(componentModel = "spring", uses = EventMapper.class)
public interface ParticipantListMapper {
    ParticipantListMapper instance = getMapper(ParticipantListMapper.class);
    List<Participant> toParticipantList(List<ParticipantDto> dtoList);
    List<ParticipantDto> toDtoList(List<Participant> modelList);
}
