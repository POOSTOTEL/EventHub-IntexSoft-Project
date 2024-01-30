package com.Eventhub.EventHubIntexSoft.service;

import com.Eventhub.EventHubIntexSoft.dto.CommentDto;
import java.util.List;
import java.util.Optional;

public interface CommentService {
  List<CommentDto> getAllComments();

  Optional<CommentDto> createComment(CommentDto commentDto);

  Optional<CommentDto> getCommentByCommentId(Long commentId);

  Optional<CommentDto> updateComment(CommentDto commentDto);

  boolean deleteCommentByCommentId(Long commentId);
}
