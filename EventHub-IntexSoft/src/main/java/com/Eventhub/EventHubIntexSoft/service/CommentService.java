package com.Eventhub.EventHubIntexSoft.service;

import com.Eventhub.EventHubIntexSoft.dto.CommentDto;
import java.util.List;

public interface CommentService {
  List<CommentDto> getAllComments();

  CommentDto createComment(CommentDto commentDto);

  CommentDto getCommentByCommentId(Long commentId);

  CommentDto updateComment(CommentDto commentDto);

  CommentDto patchComment(CommentDto commentDto);

  void deleteCommentByCommentId(Long commentId);
}
