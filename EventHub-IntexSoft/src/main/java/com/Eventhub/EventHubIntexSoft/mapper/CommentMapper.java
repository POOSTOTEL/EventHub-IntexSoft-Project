package com.Eventhub.EventHubIntexSoft.mapper;

import static org.mapstruct.factory.Mappers.getMapper;

import com.Eventhub.EventHubIntexSoft.DTO.CommentDto;
import com.Eventhub.EventHubIntexSoft.entity.Comment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommentMapper {
  CommentMapper instance = getMapper(CommentMapper.class);

  CommentDto toCommentDto(Comment comment);

  Comment toComment(CommentDto commentDTO);
}
