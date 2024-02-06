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
public class CommentValidator {
  @Autowired protected CommentRepository commentRepository;
  @Autowired protected EventRepository eventRepository;
  @Autowired protected UserRepository userRepository;

  public class EventIdValidator extends FieldValidator {
    @Override
    public void validate(Long eventId) throws EmptyDtoFieldException, NotFoundException {
      validateNullField(eventId);
      validateExistingEventById(eventId);
    }

    public void validateExistingEventById(Long eventId)
        throws EmptyDtoFieldException, NotFoundException {
      if (Objects.isNull(eventRepository.findEventByEventId(eventId))) {
        throw new NotFoundException();
      }
    }
  }

  public class UserIdValidator extends FieldValidator {
    @Override
    public void validate(Long userId) throws NotFoundException, EmptyDtoFieldException {
      validateNullField(userId);
      validateExistingUserByUserId(userId);
    }

    public void validateExistingUserByUserId(Long userId) throws NotFoundException {
      if (Objects.isNull(userRepository.findUserByUserId(userId))) {
        throw new NotFoundException();
      }
    }
  }

  public class TextCommentValidator extends FieldValidator {
    @Override
    public void validate(String comment) throws EmptyDtoFieldException {
      validateNullField(comment);
    }
  }

  public class RatingValidator extends FieldValidator {
    @Override
    public void validate(Integer rating) throws EmptyDtoFieldException {
      validateNullField(rating);
    }
  }

  public void validateCommentDtoSave(CommentDto commentDto)
      throws NotFoundException, EmptyDtoFieldException {
    new EventIdValidator().validate(commentDto.getEventId());
    new UserIdValidator().validate(commentDto.getUserId());
    new TextCommentValidator().validate(commentDto.getComment());
    new RatingValidator().validate(commentDto.getRating());
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
      new EventIdValidator().validate(commentDto.getEventId());
    }
    if (Objects.nonNull(commentDto.getUserId())) {
      new UserIdValidator().validate(commentDto.getUserId());
    }
    if (Objects.nonNull(commentDto.getComment())) {
      new TextCommentValidator().validate(commentDto.getComment());
    }
    if (Objects.nonNull(commentDto.getRating())) {
      new RatingValidator().validate(commentDto.getRating());
    }
  }

  public void validateCommentExistingByCommentId(Long commentId) throws NotFoundException {
    if (Objects.isNull(commentRepository.findCommentByCommentId(commentId))) {
      throw new NotFoundException();
    }
  }
}
