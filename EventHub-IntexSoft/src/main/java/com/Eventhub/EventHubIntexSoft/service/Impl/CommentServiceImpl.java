package com.Eventhub.EventHubIntexSoft.service.Impl;

import com.Eventhub.EventHubIntexSoft.dto.CommentDto;
import com.Eventhub.EventHubIntexSoft.entity.Comment;
import com.Eventhub.EventHubIntexSoft.mapper.CommentMapper;
import com.Eventhub.EventHubIntexSoft.repository.CommentRepository;
import com.Eventhub.EventHubIntexSoft.service.CommentService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
  private final CommentRepository commentRepository;

  public List<CommentDto> getAllComments() {
    return CommentMapper.instance.toDtoList(commentRepository.findAll());
  }

  public Optional<CommentDto> createComment(Comment comment) {
    return Optional.ofNullable(
        CommentMapper.instance.toCommentDto(commentRepository.save(comment)));
  }

  public Optional<CommentDto> getCommentByCommentId(Long commentId) {
    return Optional.ofNullable(
        CommentMapper.instance.toCommentDto(commentRepository.findCommentByCommentId(commentId)));
  }

  public Optional<CommentDto> updateComment(CommentDto commentDto) {

    return Optional.ofNullable(commentRepository.findCommentByCommentId(commentDto.getCommentId()))
        .map(
            comment -> {
              Optional.ofNullable(commentDto.getComment()).ifPresent(comment::setComment);
              Optional.ofNullable(commentDto.getRating()).ifPresent(comment::setRating);
              return CommentMapper.instance.toCommentDto(commentRepository.save(comment));
            });
  }

  @Named("commentIdListToCommentList")
  public List<Comment> commentIdListToCommentList(List<Long> ids) {
    List<Comment> comments = new ArrayList<>();
    for (Long id : ids) {
      comments.addAll(commentRepository.findAllByCommentId(id));
    }
    return comments;
  }

  @Transactional(isolation = Isolation.READ_COMMITTED)
  public boolean deleteCommentByCommentId(Long commentId) {
    return Optional.ofNullable(commentRepository.findCommentByCommentId(commentId))
        .map(
            user -> {
              commentRepository.deleteCommentByCommentId(commentId);
              return true;
            })
        .orElse(false);
  }
}
