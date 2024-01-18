package com.Eventhub.EventHubIntexSoft.mapper;

import com.Eventhub.EventHubIntexSoft.DTO.CommentDTO;
import com.Eventhub.EventHubIntexSoft.entity.Comment;

public abstract class CommentMapper {
    public static CommentDTO toDTO (Comment comment) {
        return new CommentDTO(comment.getId(), EventMapper.toDTO(comment.getEvent()), UserMapper.toDTO(comment.getUser()), comment.getComment(), comment.getRating(), comment.getCommentDate());
    }
    public static Comment toComment (CommentDTO commentDTO) {
        return new Comment(commentDTO.getId(), EventMapper.toEvent(commentDTO.getEventDTO()), UserMapper.toUser(commentDTO.getUserDTO()), commentDTO.getComment(), commentDTO.getRating(), commentDTO.getCommentDate());
    }
}
