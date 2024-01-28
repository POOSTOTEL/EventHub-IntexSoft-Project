package com.Eventhub.EventHubIntexSoft.mapper;

import static org.mapstruct.factory.Mappers.getMapper;

import com.Eventhub.EventHubIntexSoft.dto.CommentDto;
import com.Eventhub.EventHubIntexSoft.entity.Comment;
import com.Eventhub.EventHubIntexSoft.entity.Event;
import com.Eventhub.EventHubIntexSoft.entity.User;
import com.Eventhub.EventHubIntexSoft.service.Impl.EventServiceImpl;
import com.Eventhub.EventHubIntexSoft.service.Impl.UserServiceImpl;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(
    componentModel = "spring",
    uses = {UserServiceImpl.class, EventServiceImpl.class})
public interface CommentMapper {
  CommentMapper instance = getMapper(CommentMapper.class);

  @Mapping(source = "event", target = "eventId", qualifiedByName = "eventToEventId")
  @Mapping(source = "user", target = "userId", qualifiedByName = "userToUserId")
  CommentDto toCommentDto(Comment comment);

  @Mapping(source = "eventId", target = "event", qualifiedByName = "findEventByEventId")
  @Mapping(source = "userId", target = "user", qualifiedByName = "findUserByUserId")
  Comment toComment(CommentDto commentDTO);

  List<CommentDto> toDtoList(List<Comment> modelList);

  List<Comment> toCommentList(List<CommentDto> dtoList);

  @Named("eventToEventId")
  default Long eventToEventId(Event event) {
    return event.getEventId();
  }

  @Named("userToUserId")
  default Long userToUserId(User user) {
    return user.getUserId();
  }
}
