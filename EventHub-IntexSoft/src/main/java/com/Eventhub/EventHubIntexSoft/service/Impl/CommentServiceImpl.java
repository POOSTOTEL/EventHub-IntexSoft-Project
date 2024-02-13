package com.Eventhub.EventHubIntexSoft.service.Impl;

import com.Eventhub.EventHubIntexSoft.dto.CommentDto;
import com.Eventhub.EventHubIntexSoft.entity.Comment;
import com.Eventhub.EventHubIntexSoft.mapper.CommentMapper;
import com.Eventhub.EventHubIntexSoft.repository.CommentRepository;
import com.Eventhub.EventHubIntexSoft.service.CommentService;
import java.beans.FeatureDescriptor;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
  private final CommentRepository commentRepository;
  private final CommentMapper commentMapper;

  public List<CommentDto> getAllComments() {
    return commentMapper.toDtoList(commentRepository.findAll());
  }

  public CommentDto createComment(CommentDto commentDto) {
    commentDto.setCreationTime(LocalDateTime.now());
    return commentMapper.toCommentDto(commentRepository.save(commentMapper.toComment(commentDto)));
  }

  public CommentDto getCommentByCommentId(Long commentId) {
    return commentMapper.toCommentDto(commentRepository.findCommentByCommentId(commentId));
  }

  public CommentDto updateComment(CommentDto commentDto) {
    Comment comment = commentRepository.findCommentByCommentId(commentDto.getCommentId());
    BeanUtils.copyProperties(commentDto, comment, "commentId", "creationTime", "updateTime");
    comment.setUpdateTime(LocalDateTime.now());
    return commentMapper.toCommentDto(commentRepository.save(comment));
  }

  public CommentDto patchComment(CommentDto commentDto) {
    Comment comment = commentRepository.findCommentByCommentId(commentDto.getCommentId());
    BeanUtils.copyProperties(commentDto, comment, getNullProperties(commentDto));
    comment.setUpdateTime(LocalDateTime.now());
    return commentMapper.toCommentDto(commentRepository.save(comment));
  }

  @Transactional(isolation = Isolation.READ_COMMITTED)
  public void deleteCommentByCommentId(Long commentId) {
    commentRepository.deleteCommentByCommentId(commentId);
  }

  private String[] getNullProperties(CommentDto commentDto) {
    final BeanWrapper wrapper = new BeanWrapperImpl(commentDto);
    return Stream.of(wrapper.getPropertyDescriptors())
        .map(FeatureDescriptor::getName)
        // todo вырезать костыль в виде тройного ИЛИ
        .filter(
            name ->
                wrapper.getPropertyValue(name) == null
                    || wrapper.getPropertyValue(name) == "commentId"
                    || wrapper.getPropertyValue(name) == "creationTime"
                    || wrapper.getPropertyValue(name) == "updateTime")
        .toArray(String[]::new);
  }
}
