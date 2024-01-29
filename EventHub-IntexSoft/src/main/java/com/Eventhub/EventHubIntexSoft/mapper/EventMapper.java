package com.Eventhub.EventHubIntexSoft.mapper;

import static org.mapstruct.factory.Mappers.getMapper;

import com.Eventhub.EventHubIntexSoft.dto.EventDto;
import com.Eventhub.EventHubIntexSoft.entity.Comment;
import com.Eventhub.EventHubIntexSoft.entity.Event;
import com.Eventhub.EventHubIntexSoft.entity.Participant;
import com.Eventhub.EventHubIntexSoft.service.Impl.CommentServiceImpl;
import com.Eventhub.EventHubIntexSoft.service.Impl.ParticipantServiceImpl;
import java.util.ArrayList;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(
    componentModel = "spring",
    uses = {CommentServiceImpl.class, ParticipantServiceImpl.class})
public interface EventMapper {
  EventMapper instance = getMapper(EventMapper.class);

  @Mapping(
      source = "eventComments",
      target = "eventComments",
      qualifiedByName = "commentListToCommentIdList")
  @Mapping(
      source = "eventParticipants",
      target = "eventParticipants",
      qualifiedByName = "participantListToParticipantIdList")
  EventDto toEventDto(Event event);

  @Mapping(
      source = "eventComments",
      target = "eventComments",
      qualifiedByName = "commentIdListToCommentList")
  @Mapping(
      source = "eventParticipants",
      target = "eventParticipants",
      qualifiedByName = "participantIdListToParticipantList")
  Event toEvent(EventDto eventDto);

  List<Event> toEventList(List<EventDto> dtoList);

  List<EventDto> toDtoList(List<Event> modelList);

  @Named("commentListToCommentIdList")
  default List<Long> commentListToCommentIdList(List<Comment> comments) {
    List<Long> ids = new ArrayList<>();
    for (Comment comment : comments) {
      ids.add(comment.getCommentId());
    }
    return ids;
  }

  @Named("participantListToParticipantIdList")
  default List<Long> participantListToParticipantIdList(List<Participant> participants) {
    List<Long> ids = new ArrayList<>();
    for (Participant participant : participants) {
      ids.add(participant.getParticipantId());
    }
    return ids;
  }
}
