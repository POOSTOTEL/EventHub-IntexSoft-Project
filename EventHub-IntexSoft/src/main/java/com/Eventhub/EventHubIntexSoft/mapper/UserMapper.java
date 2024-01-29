package com.Eventhub.EventHubIntexSoft.mapper;

import static org.mapstruct.factory.Mappers.getMapper;

import com.Eventhub.EventHubIntexSoft.dto.UserDto;
import com.Eventhub.EventHubIntexSoft.entity.Comment;
import com.Eventhub.EventHubIntexSoft.entity.Participant;
import com.Eventhub.EventHubIntexSoft.entity.User;
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
public interface UserMapper {
  UserMapper instance = getMapper(UserMapper.class);

  @Mapping(
      source = "userComments",
      target = "userComments",
      qualifiedByName = "commentIdListToCommentList")
  @Mapping(
      source = "userParticipants",
      target = "userParticipants",
      qualifiedByName = "participantIdListToParticipantList")
  User toUser(UserDto userDTO);

  @Mapping(
      source = "userComments",
      target = "userComments",
      qualifiedByName = "commentListToCommentIdList")
  @Mapping(
      source = "userParticipants",
      target = "userParticipants",
      qualifiedByName = "participantListToParticipantIdList")
  UserDto toUserDto(User user);

  List<User> toUsertList(List<UserDto> dtoList);

  List<UserDto> toDtoList(List<User> modelList);

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
