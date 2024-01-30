package com.Eventhub.EventHubIntexSoft.mapper;

import com.Eventhub.EventHubIntexSoft.dto.CommentDto;
import com.Eventhub.EventHubIntexSoft.entity.Comment;
import com.Eventhub.EventHubIntexSoft.entity.Event;
import com.Eventhub.EventHubIntexSoft.entity.User;
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
public abstract class CommentMapper {

  @Autowired protected UserService userService;

  @Autowired protected EventService eventService;

  @Mapping(source = "event", target = "eventId", qualifiedByName = "eventToEventId")
  @Mapping(source = "user", target = "userId", qualifiedByName = "userToUserId")
  public abstract CommentDto toCommentDto(Comment comment);

  @Mapping(source = "eventId", target = "event", qualifiedByName = "findEventByEventId")
  @Mapping(source = "userId", target = "user", qualifiedByName = "findUserByUserId")
  public abstract Comment toComment(CommentDto commentDTO);

  public abstract List<CommentDto> toDtoList(List<Comment> modelList);

  public abstract List<Comment> toCommentList(List<CommentDto> dtoList);

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
}
