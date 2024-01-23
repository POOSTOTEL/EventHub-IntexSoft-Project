package com.Eventhub.EventHubIntexSoft.mapper;

import static org.mapstruct.factory.Mappers.getMapper;

import com.Eventhub.EventHubIntexSoft.DTO.ParticipantDto;
import com.Eventhub.EventHubIntexSoft.entity.Participant;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(
    componentModel = "spring",
    uses = {UserMapper.class, EventMapper.class})
public interface ParticipantListMapper {
  ParticipantListMapper instance = getMapper(ParticipantListMapper.class);

  List<Participant> toParticipantList(List<ParticipantDto> dtoList);

  List<ParticipantDto> toDtoList(List<Participant> modelList);
}
