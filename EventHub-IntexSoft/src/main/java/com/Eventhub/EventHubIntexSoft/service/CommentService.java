package com.Eventhub.EventHubIntexSoft.service;

import com.Eventhub.EventHubIntexSoft.DTO.CommentDto;
import com.Eventhub.EventHubIntexSoft.entity.Comment;
import java.util.List;
import java.util.Optional;

public interface CommentService {
  List<CommentDto> getAllComments();

  Optional<CommentDto> createComment(Comment comment);

  Optional<CommentDto> getCommentById(Long id);

  Optional<CommentDto> updateComment(CommentDto commentDto);

  boolean deleteCommentById(Long id);
}
