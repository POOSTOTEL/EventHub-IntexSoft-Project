package com.Eventhub.EventHubIntexSoft.validator;

import com.Eventhub.EventHubIntexSoft.dto.CommentDto;
import com.Eventhub.EventHubIntexSoft.exception.EmptyDtoFieldException;
import com.Eventhub.EventHubIntexSoft.exception.NotFoundException;
import com.Eventhub.EventHubIntexSoft.repository.CommentRepository;
import com.Eventhub.EventHubIntexSoft.repository.EventRepository;
import com.Eventhub.EventHubIntexSoft.repository.UserRepository;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentValidator extends FieldValidator {
  @Autowired protected CommentRepository commentRepository;
  @Autowired protected EventRepository eventRepository;
  @Autowired protected UserRepository userRepository;

  public void validateEventId(Long eventId) throws EmptyDtoFieldException, NotFoundException {
    validateNullField(eventId);
    validateExistingEventById(eventId);
  }

  public void validateUserId(Long userId) throws NotFoundException, EmptyDtoFieldException {
    validateNullField(userId);
    validateExistingUserByUserId(userId);
  }

  public void validateCommentText(String comment) throws EmptyDtoFieldException {
    validateNullField(comment);
  }

  public void validateRating(Integer rating) throws EmptyDtoFieldException {
    validateNullField(rating);
  }

  public void validateCommentDtoSave(CommentDto commentDto)
      throws NotFoundException, EmptyDtoFieldException {
    validateEventId(commentDto.getEventId());
    validateUserId(commentDto.getUserId());
    validateCommentText(commentDto.getComment());
    validateRating(commentDto.getRating());
  }

  public void validateCommentDtoUpdate(CommentDto commentDto)
      throws NotFoundException, EmptyDtoFieldException {
    validateCommentExistingByCommentId(commentDto.getCommentId());
    validateCommentDtoSave(commentDto);
  }

  public void validateCommentDtoPatch(CommentDto commentDto)
      throws NotFoundException, EmptyDtoFieldException {
    validateCommentExistingByCommentId(commentDto.getCommentId());
    if (Objects.nonNull(commentDto.getEventId())) {
      validateEventId(commentDto.getEventId());
    }
    if (Objects.nonNull(commentDto.getUserId())) {
      validateUserId(commentDto.getUserId());
    }
    if (Objects.nonNull(commentDto.getComment())) {
      validateCommentText(commentDto.getComment());
    }
    if (Objects.nonNull(commentDto.getRating())) {
      validateRating(commentDto.getRating());
    }
  }

  public void validateCommentExistingByCommentId(Long commentId) throws NotFoundException {
    if (Objects.isNull(commentRepository.findCommentByCommentId(commentId))) {
      throw new NotFoundException();
    }
  }

  public void validateExistingEventById(Long eventId)
      throws NotFoundException {
    if (Objects.isNull(eventRepository.findEventByEventId(eventId))) {
      throw new NotFoundException();
    }
  }

  public void validateExistingUserByUserId(Long userId) throws NotFoundException {
    if (Objects.isNull(userRepository.findUserByUserId(userId))) {
      throw new NotFoundException();
    }
  }
}
