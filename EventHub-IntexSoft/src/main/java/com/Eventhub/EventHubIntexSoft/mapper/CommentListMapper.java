package com.Eventhub.EventHubIntexSoft.mapper;

import static org.mapstruct.factory.Mappers.getMapper;

import com.Eventhub.EventHubIntexSoft.DTO.CommentDto;
import com.Eventhub.EventHubIntexSoft.entity.Comment;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(
    componentModel = "spring",
    uses = {UserMapper.class, EventMapper.class})
public interface CommentListMapper {
  CommentListMapper instance = getMapper(CommentListMapper.class);

  List<Comment> toCommentList(List<CommentDto> dtoList);

  List<CommentDto> toDtoList(List<Comment> modelList);
}
