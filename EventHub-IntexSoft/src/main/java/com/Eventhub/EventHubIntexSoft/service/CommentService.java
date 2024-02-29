package com.Eventhub.EventHubIntexSoft.service;

import com.Eventhub.EventHubIntexSoft.dto.CommentDto;
import org.springframework.data.domain.Page;

public interface CommentService {
  Page<CommentDto> getAllComments(Integer offset, Integer limit);

  CommentDto createComment(CommentDto commentDto);

  CommentDto getCommentByCommentId(Long commentId);

  CommentDto updateComment(CommentDto commentDto);

  CommentDto patchComment(CommentDto commentDto);

  void deleteCommentByCommentId(Long commentId);
}
