package com.Eventhub.EventHubIntexSoft.validator;

import com.Eventhub.EventHubIntexSoft.dto.EventDto;
import com.Eventhub.EventHubIntexSoft.entity.Event;
import com.Eventhub.EventHubIntexSoft.exception.EmptyDtoFieldException;
import com.Eventhub.EventHubIntexSoft.exception.NonUniqValueException;
import com.Eventhub.EventHubIntexSoft.exception.NotFoundException;
import com.Eventhub.EventHubIntexSoft.repository.EventRepository;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventValidator extends FieldValidator {
  @Autowired protected EventRepository eventRepository;

  String dateTimePattern = "dd-MM-yyyy HH:mm:ss";
  DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(dateTimePattern);

  public void validateDescription(String description) throws EmptyDtoFieldException {
    validateNullField(description);
  }

  public void validateTitle(String title, Long eventId)
      throws EmptyDtoFieldException, NonUniqValueException {
    validateNullField(title);
    validateUniqTitle(title, eventId);
  }

  public void validateEventDate(String eventDate) throws EmptyDtoFieldException {
    validateNullField(eventDate);
  }

  public void validateLocation(String location) throws EmptyDtoFieldException {
    validateNullField(location);
  }

  public void validateEventDtoSave(EventDto eventDto)
      throws EmptyDtoFieldException, NonUniqValueException {
    validateTitle(eventDto.getTitle(), eventDto.getEventId());
    validateDescription(eventDto.getDescription());
    validateEventDate(eventDto.getEventDate().format(dateTimeFormatter));
    validateLocation(eventDto.getLocation());
  }

  public void validateEventDtoUpdate(EventDto eventDto)
      throws EmptyDtoFieldException, NonUniqValueException, NotFoundException {
    validateEventExistingByEventId(eventDto.getEventId());
    validateEventDtoSave(eventDto);
  }

  public void validateEventDtoPatch(EventDto eventDto)
      throws NonUniqValueException, NotFoundException, EmptyDtoFieldException {
    validateEventExistingByEventId(eventDto.getEventId());
    if (Objects.nonNull(eventDto.getTitle())) {
      validateTitle(eventDto.getTitle(), eventDto.getEventId());
    }
    if (Objects.nonNull(eventDto.getDescription())) {
      validateDescription(eventDto.getDescription());
    }
    if (Objects.nonNull(eventDto.getEventDate())) {
      validateEventDate(eventDto.getEventDate().format(dateTimeFormatter));
    }
    if (Objects.nonNull(eventDto.getLocation())) {
      validateLocation(eventDto.getLocation());
    }
  }

  public void validateEventExistingByEventId(Long eventId) throws NotFoundException {
    if (Objects.isNull(eventRepository.findEventByEventId(eventId))) {
      throw new NotFoundException();
    }
  }

  public void validateUniqTitle(String title, Long eventId) throws NonUniqValueException {
    Event event = eventRepository.findEventByTitle(title);
    if (Objects.nonNull(event) && !event.getEventId().equals(eventId)) {
      throw new NonUniqValueException();
    }
  }
}
