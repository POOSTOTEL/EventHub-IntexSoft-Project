package com.Eventhub.EventHubIntexSoft.mapper;

import com.Eventhub.EventHubIntexSoft.DTO.CommentDto;
import com.Eventhub.EventHubIntexSoft.entity.Comment;
import com.Eventhub.EventHubIntexSoft.entity.User;
import org.mapstruct.Mapper;

import java.util.List;

import static org.mapstruct.factory.Mappers.getMapper;

@Mapper(componentModel = "spring", uses = {UserMapper.class, EventMapper.class})
public interface CommentListMapper {
    CommentListMapper instance = getMapper(CommentListMapper.class);
    List<Comment> toCommentList(List<CommentDto> dtoList);
    List<CommentDto> toDtoList(List<Comment> modelList);
}
