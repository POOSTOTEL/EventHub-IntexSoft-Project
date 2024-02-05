package com.Eventhub.EventHubIntexSoft.validator.event;

import com.Eventhub.EventHubIntexSoft.dto.EventDto;
import com.Eventhub.EventHubIntexSoft.exception.EmptyDtoFieldException;
import com.Eventhub.EventHubIntexSoft.exception.NonUniqValueException;
import com.Eventhub.EventHubIntexSoft.exception.NotFoundException;
import com.Eventhub.EventHubIntexSoft.repository.EventRepository;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventValidator {
  @Autowired protected EventRepository eventRepository;
  @Autowired protected TitleValidator titleValidator;
  @Autowired protected DescriptionValidator descriptionValidator;
  @Autowired protected EventDateValidator eventDateValidator;
  @Autowired protected LocationValidator locationValidator;
  String dateTimePattern = "dd-MM-yyyy HH:mm:ss";
  DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(dateTimePattern);

  public void validateEventDtoSave(EventDto eventDto)
      throws EmptyDtoFieldException, NonUniqValueException {
    titleValidator.validate(eventDto.getTitle(), eventDto.getEventId());
    descriptionValidator.validate(eventDto.getDescription(), eventDto.getEventId());
    eventDateValidator.validate(
        eventDto.getEventDate().format(dateTimeFormatter), eventDto.getEventId());
    locationValidator.validate(eventDto.getLocation(), eventDto.getEventId());
  }

  public void validateEventDtoUpdate(EventDto eventDto)
      throws EmptyDtoFieldException, NonUniqValueException, NotFoundException {
    validateEventExistingById(eventDto.getEventId());
    validateEventDtoSave(eventDto);
  }

  public void validateEventDtoPatch(EventDto eventDto)
      throws NonUniqValueException, NotFoundException, EmptyDtoFieldException {
    validateEventExistingById(eventDto.getEventId());
    if (Objects.nonNull(eventDto.getTitle())) {
      titleValidator.validate(eventDto.getTitle(), eventDto.getEventId());
    }
    if (Objects.nonNull(eventDto.getDescription())) {
      descriptionValidator.validate(eventDto.getDescription(), eventDto.getEventId());
    }
    if (Objects.nonNull(eventDto.getEventDate())) {
      eventDateValidator.validate(
          eventDto.getEventDate().format(dateTimeFormatter), eventDto.getEventId());
    }
    if (Objects.nonNull(eventDto.getLocation())) {
      locationValidator.validate(eventDto.getLocation(), eventDto.getEventId());
    }
  }

  public void validateEventExistingById(Long eventId) throws NotFoundException {
    if (Objects.isNull(eventRepository.findEventByEventId(eventId))) {
      throw new NotFoundException();
    }
  }
}
