package com.Eventhub.EventHubIntexSoft.mapper;

import com.Eventhub.EventHubIntexSoft.dto.ParticipantDto;
import com.Eventhub.EventHubIntexSoft.entity.Event;
import com.Eventhub.EventHubIntexSoft.entity.Participant;
import com.Eventhub.EventHubIntexSoft.entity.ParticipantStatus;
import com.Eventhub.EventHubIntexSoft.entity.User;
import com.Eventhub.EventHubIntexSoft.repository.ParticipantStatusRepository;
import com.Eventhub.EventHubIntexSoft.service.EventService;
import com.Eventhub.EventHubIntexSoft.service.UserService;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public abstract class ParticipantMapper {

  @Autowired protected UserService userService;

  @Autowired protected EventService eventService;

  @Autowired protected ParticipantStatusRepository participantStatusRepository;

  @Mapping(source = "eventId", target = "event", qualifiedByName = "findEventByEventId")
  @Mapping(source = "userId", target = "user", qualifiedByName = "findUserByUserId")
  @Mapping(source = "status", target = "status", qualifiedByName = "stringToParticipantStatus")
  public abstract Participant toParticipant(ParticipantDto participantDto);

  @Mapping(source = "event", target = "eventId", qualifiedByName = "eventToEventId")
  @Mapping(source = "user", target = "userId", qualifiedByName = "userToUserId")
  @Mapping(source = "status", target = "status", qualifiedByName = "participantStatusToString")
  public abstract ParticipantDto toParticipantDto(Participant participant);

  public abstract List<Participant> toParticipantList(List<ParticipantDto> dtoList);

  public abstract List<ParticipantDto> toDtoList(List<Participant> modelList);

  @Named("eventToEventId")
  protected Long eventToEventId(Event event) {
    return event.getEventId();
  }

  @Named("userToUserId")
  protected Long userToUserId(User user) {
    return user.getUserId();
  }

  @Named("findUserByUserId")
  protected User findUserByUserId(Long userId) {
    return userService.findUserByUserId(userId);
  }

  @Named("findEventByEventId")
  protected Event findEventByEventId(Long eventId) {
    return eventService.findEventByEventId(eventId);
  }

  @Named("stringToParticipantStatus")
  protected ParticipantStatus stringToParticipantStatus(String status) {
    return participantStatusRepository.findParticipantStatusByStatus(status);
  }

  @Named("participantStatusToString")
  protected String participantStatusToString(ParticipantStatus participantStatus) {
    return participantStatus.getStatus();
  }
}
