package com.Eventhub.EventHubIntexSoft.mapper;

import static org.mapstruct.factory.Mappers.getMapper;

import com.Eventhub.EventHubIntexSoft.dto.ParticipantDto;
import com.Eventhub.EventHubIntexSoft.entity.Event;
import com.Eventhub.EventHubIntexSoft.entity.Participant;
import com.Eventhub.EventHubIntexSoft.entity.User;
import java.util.List;

import com.Eventhub.EventHubIntexSoft.service.Impl.EventServiceImpl;
import com.Eventhub.EventHubIntexSoft.service.Impl.UserServiceImpl;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(
    componentModel = "spring",
    uses = {UserServiceImpl.class, EventServiceImpl.class})
public interface ParticipantMapper {
  ParticipantMapper instance = getMapper(ParticipantMapper.class);

  @Mapping(source = "eventId", target = "event", qualifiedByName = "findEventByEventId")
  @Mapping(source = "userId", target = "user", qualifiedByName = "findUserByUserId")
  Participant toParticipant(ParticipantDto participantDto);

  @Mapping(source = "event", target = "eventId", qualifiedByName = "eventToEventId")
  @Mapping(source = "user", target = "userId", qualifiedByName = "userToUserId")
  ParticipantDto toParticipantDto(Participant participant);

  List<Participant> toParticipantList(List<ParticipantDto> dtoList);

  List<ParticipantDto> toDtoList(List<Participant> modelList);

  @Named("eventToEventId")
  default Long eventToEventId(Event event) {
    return event.getEventId();
  }

  @Named("userToUserId")
  default Long userToUserId(User user) {
    return user.getUserId();
  }
}
